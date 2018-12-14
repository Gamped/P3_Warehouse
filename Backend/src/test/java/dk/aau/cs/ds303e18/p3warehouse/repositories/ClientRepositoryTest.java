package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.models.orders.OrderLine;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.users.ContactInformation;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Publisher;
import dk.aau.cs.ds303e18.p3warehouse.models.users.UserType;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ClientRepositoryTest { }/*

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    PublisherRepository publisherRepository;

    public Client makeClient() {
        ObjectId id = new ObjectId();
        Client client = new Client(id);
        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setNickName("Hans");
        contactInformation.setEmail("fes@gr.gdr");
        contactInformation.setPhoneNumber("15334888");
        contactInformation.setAddress("møllevej 4");
        contactInformation.setZipCode("5497");
        contactInformation.setCity("Aalborg");

        client.setUserName("Client");
        client.setPassword("3wdgr4");
        client.setUserType(UserType.CLIENT);
        client.setContactInformation(contactInformation);

        return client;
    }

    public Product makeProduct() {
        ObjectId productId = new ObjectId();

        Product product = new Product(productId);
        product.setQuantity(400);
        product.setProductName("cycling news");
        product.setProductId("342525");

        return product;
    }

    public Order makeOrder() {
        ObjectId orderId = new ObjectId();
        Order order = new Order(orderId);
        order.setTitle("flyers");
        order.setOrderId("3255");
        order.setAddress("musvej 3");
        order.setDate(new Date());

        return order;
    }

    public Publisher makePublisher() {
        ObjectId publisherId = new ObjectId();
        Publisher publisher = new Publisher(publisherId);
        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setNickName("karen");
        contactInformation.setEmail("cyc@fff.dd");
        contactInformation.setPhoneNumber("2564866235");
        contactInformation.setAddress("mosevej 54");
        contactInformation.setZipCode("5495");
        publisher.setUserName("Publisher");
        publisher.setPassword("fee2224");
        publisher.setUserType(UserType.PUBLISHER);
        publisher.setContactInformation(contactInformation);

        return publisher;
    }

    @Test
    public void testFindAllClients() {
        List<Client> clients = clientRepository.findAll();
        assertThat(clients.size(), is(greaterThanOrEqualTo(0)));
    }

    @Test
    public void testFindClientById() {
        Client client = makeClient();

        clientRepository.save(client);

        Client retrievedClient = clientRepository.findById(client.getId()).orElse(null);
        assertEquals(client.getId(), retrievedClient.getId());
    }

    @Test
    public void testFindClientInformation(){
        Client client = makeClient();

        clientRepository.save(client);

        Client retrievedClient = clientRepository.findById(client.getHexId());

        assertEquals(client.getUserName(), retrievedClient.getUserName());
        assertEquals(client.getUserType(), retrievedClient.getUserType());
        assertEquals(client.getPassword(), retrievedClient.getPassword());
        assertEquals(client.getContactInformation(), retrievedClient.getContactInformation());
    }

    @Test
    public void testClientProduct(){
        Product product = makeProduct();
        Product secondProduct = makeProduct();
        Client client = makeClient();
        client.addProduct(product);
        client.addProduct(secondProduct);

        productRepository.save(product);
        productRepository.save(secondProduct);
        clientRepository.save(client);

        Client retrievedClient = clientRepository.findById(client.getHexId());
        Stream<Product> productStream = retrievedClient.getProductStream();
        List<Product> productList = productStream.collect(Collectors.toList());

        assertNotNull(productList);
        assertEquals(2, productList.size());
    }

    @Test
    public void testClientPublisher() {
        Client client = makeClient();
        Publisher publisher = makePublisher();
        client.setPublisher(publisher);

        clientRepository.save(client);
        publisherRepository.save(publisher);

        Client retrievedClient = clientRepository.findById(client.getHexId());

        assertNotNull(retrievedClient.getPublisher());
        assertEquals(publisher.getHexId(), retrievedClient.getPublisher().getHexId());
        assertEquals(publisher, retrievedClient.getPublisher());
        assertEquals(publisher.getUserName(), retrievedClient.getPublisher().getUserName());
        assertEquals(publisher.getPassword(), retrievedClient.getPublisher().getPassword());
        assertEquals(publisher.getContactInformation(), retrievedClient.getPublisher().getContactInformation());
    }

    @Test
    public void testClientOrder() {
        Product product = makeProduct();
        Product secondProduct = makeProduct();
        Order order = makeOrder();
        Order secondOrder = makeOrder();
        Client client = makeClient();

        OrderLine orderLine = new OrderLine(product, 250);
        OrderLine secondOrderLine = new OrderLine(secondProduct, 222);
        order.setOrderLines(Collections.singleton(orderLine));
        secondOrder.setOrderLines(Collections.singleton(secondOrderLine));

        client.addOrder(order);
        client.addOrder(secondOrder);

        productRepository.save(product);
        productRepository.save(secondProduct);
        orderRepository.save(order);
        orderRepository.save(secondOrder);
        clientRepository.save(client);

        Client retrievedClient = clientRepository.findById(client.getHexId());
        Stream<Order> orderStream = retrievedClient.getOrderStream();
        List<Order> orderList = orderStream.collect(Collectors.toList());

        assertNotNull(orderList);
        assertEquals(2, orderList.size());
    }

    @Test
    public void testUserRef() {
        Client client = makeClient();
        Publisher publisher = makePublisher();

        publisher.addClient(client);
        client.setPublisher(publisher);

        clientRepository.save(client);
        publisherRepository.save(publisher);

        Client retrievedClient = clientRepository.findById(client.getId()).orElse(null);
        System.out.println(retrievedClient.getPublisherRef().getUserHexId());
        assertEquals(client.getPublisherRef().getUserHexId(), retrievedClient.getPublisherRef().getUserHexId());
    }

    @Test
    public void testDeleteAllClient(){
        clientRepository.deleteAll();

        List<Client> clientList = new ArrayList<>();
        assertEquals(clientList, clientRepository.findAll());
    }
} */
