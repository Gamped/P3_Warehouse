package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.models.orders.OrderLine;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.users.ContactInformation;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Publisher;
import dk.aau.cs.ds303e18.p3warehouse.models.users.UserType;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ClientRepositoryTest {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    PublisherRepository publisherRepository;

    @Test
    public void testFindClientInformation(){
        ObjectId id = new ObjectId();
        Client client = new Client(id);
        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setEmail("fes@gr.gdr");
        contactInformation.setPhoneNumber("153348889");
        contactInformation.setAddress("møllevej 4");
        contactInformation.setZipCode("5497");

        client.setUserName("help");
        client.setPassword("3wdgr4");
        client.setUserType(UserType.CLIENT);
        client.setContactInformation(contactInformation);

        clientRepository.save(client);

        Client retrievedClient = clientRepository.findById(client.getHexId());

        Assert.assertEquals(client.getUserName(), retrievedClient.getUserName());
        Assert.assertEquals(client.getUserType(), retrievedClient.getUserType());
        Assert.assertEquals(client.getPassword(), retrievedClient.getPassword());
        Assert.assertEquals(client.getContactInformation(), retrievedClient.getContactInformation());

        clientRepository.deleteById(id);
    }

    @Test
    public void testClientProduct(){
        ObjectId id = new ObjectId();
        ObjectId productId = new ObjectId();
        ObjectId clientId = new ObjectId();

        Product product = new Product(productId);
        Product secondProduct = new Product(id);
        product.setQuantity(4);
        product.setProductName("cycling news");
        List<Product> productList = new ArrayList<>();
        productList.add(secondProduct);
        productList.add(product);
        Client client = new Client(clientId);
        client.addProduct(product);
        client.addProduct(secondProduct);

        productRepository.save(product);
        productRepository.save(secondProduct);
        clientRepository.save(client);

        Client retrievedClient = clientRepository.findById(client.getHexId());
        Stream<Product> productStream = retrievedClient.getProductStream();
        List<Product> productIterable = productStream.collect(Collectors.toList());

        Assert.assertEquals(2, productIterable.size());
        Assert.assertEquals(productList, productIterable);

        productRepository.deleteById(id);
        productRepository.deleteById(productId);
        clientRepository.deleteById(clientId);
    }

    @Test
    public void testClientPublisher() {
        ObjectId clientId = new ObjectId();
        ObjectId publisherId = new ObjectId();
        Client client = new Client(clientId);
        Publisher publisher = new Publisher(publisherId);
        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setEmail("cyc@fff.dd");
        contactInformation.setPhoneNumber("2564866235");
        contactInformation.setAddress("hostel");
        contactInformation.setZipCode("5495");
        publisher.setUserName("Cycpub");
        publisher.setPassword("fee2224");
        publisher.setContactInformation(contactInformation);
        client.setPublisher(publisher);

        clientRepository.save(client);
        publisherRepository.save(publisher);

        Client retrievedClient = clientRepository.findById(client.getHexId());

        Assert.assertNotNull(retrievedClient.getPublisher());
        Assert.assertEquals(publisher.getHexId(), retrievedClient.getPublisher().getHexId());
        Assert.assertEquals(publisher, retrievedClient.getPublisher());
        Assert.assertEquals(publisher.getUserName(), retrievedClient.getPublisher().getUserName());
        Assert.assertEquals(publisher.getPassword(), retrievedClient.getPublisher().getPassword());
        Assert.assertEquals(publisher.getContactInformation(), retrievedClient.getPublisher().getContactInformation());

        publisherRepository.deleteById(publisherId);
        clientRepository.deleteById(clientId);
    }

    @Test
    public void testClientOrder() {
        ObjectId id = new ObjectId();
        ObjectId orderId = new ObjectId();
        ObjectId clientId = new ObjectId();
        ObjectId objectId = new ObjectId();
        ObjectId productId = new ObjectId();

        Order order = new Order(id);
        Order secondOrder = new Order(orderId);
        Client client = new Client(clientId);
        Product product = new Product(objectId);
        Product secondProduct = new Product(productId);

        product.setProductName("hio");
        secondProduct.setProductName("asf");
        OrderLine orderLine = new OrderLine(product, 5);
        OrderLine secondOrderLine = new OrderLine(secondProduct, 10);

        order.setTitle("fesf");
        secondOrder.setTitle("fsef");
        order.setOrderLines(Collections.singleton(orderLine));
        secondOrder.setOrderLines(Collections.singleton(secondOrderLine));

        List<Order> orders = new ArrayList<>();
        orders.add(secondOrder);
        orders.add(order);
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

        Assert.assertNotNull(orderList);
        Assert.assertEquals(2, orderList.size());
        Assert.assertEquals(order.getTitle(), orderList.get(0).getTitle());
        Assert.assertEquals(orders, orderList);

        orderRepository.deleteAll(orders);
        clientRepository.deleteById(clientId);
        productRepository.deleteById(objectId);
        productRepository.deleteById(productId);
    }

    @Test
    public void testDeleteAllClient(){
        clientRepository.deleteAll();

        List<Client> clientList = new ArrayList<>();
        Assert.assertEquals(clientList, clientRepository.findAll());
    }
}
