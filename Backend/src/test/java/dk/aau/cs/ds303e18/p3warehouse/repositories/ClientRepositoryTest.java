package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.models.orders.OrderLine;
import dk.aau.cs.ds303e18.p3warehouse.models.users.*;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockClientData.makeClient;
import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockClientData.makeSecondClient;
import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockClientData.makeThirdClient;
import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockOrderData.makeOrder;
import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockOrderData.makeSecondOrder;
import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockProductData.makeProduct;
import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockProductData.makeSecondProduct;
import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockPublisherData.makePublisher;
import static org.junit.Assert.*;

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
    @Autowired
    UserRepository userRepository;

    @Before
    public void deleteAll() {
        productRepository.deleteAll();
        publisherRepository.deleteAll();
        clientRepository.deleteAll();
        orderRepository.deleteAll();
        userRepository.deleteAll();
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
    }

    @Test
    public void testClientProduct(){
        Product product = makeProduct();
        Product secondProduct = makeSecondProduct();
        Client client = makeClient();
        client.addProduct(product);
        client.addProduct(secondProduct);
        product.setOwner(client);
        secondProduct.setOwner(client);

        productRepository.save(product);
        productRepository.save(secondProduct);
        clientRepository.save(client);

        Client retrievedClient = clientRepository.findById(client.getHexId());
        Stream<Product> productStream = retrievedClient.getProductStream();
        List<Product> productList = productStream.collect(Collectors.toList());

        assertEquals(2, productList.size());
    }

    @Test
    public void testClientPublisher() {
        Client client = makeClient();
        Publisher publisher = makePublisher();
        client.setPublisher(publisher);
        publisher.addClient(client);

        clientRepository.save(client);
        publisherRepository.save(publisher);

        Client retrievedClient = clientRepository.findById(client.getHexId());

        assertEquals(publisher.getHexId(), retrievedClient.getPublisher().getHexId());
    }

    @Test
    public void testClientOrder() {
        Product product = makeProduct();
        Product secondProduct = makeSecondProduct();
        Client client = makeClient();
        Order order = makeOrder();
        Order secondOrder = makeSecondOrder();

        OrderLine orderLine = new OrderLine(product, 250);
        OrderLine secondOrderLine = new OrderLine(secondProduct, 15);

        ArrayList<OrderLine> a = new ArrayList<>();
        ArrayList<OrderLine> b = new ArrayList<>();

        a.add(orderLine);
        b.add(secondOrderLine);
        order.setOrderLines(a);
        secondOrder.setOrderLines(b);

        client.addOrder(order);
        client.addOrder(secondOrder);
        client.addProduct(product);
        client.addProduct(secondProduct);

        productRepository.save(product);
        productRepository.save(secondProduct);
        orderRepository.save(order);
        orderRepository.save(secondOrder);
        clientRepository.save(client);

        Client retrievedClient = clientRepository.findById(client.getHexId());
        Stream<Order> orderStream = retrievedClient.getOrderStream();
        List<Order> orderList = orderStream.collect(Collectors.toList());

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
        assertEquals(client.getPublisherRef().getUserHexId(), retrievedClient.getPublisherRef().getUserHexId());
    }

    @Test
    public void testFindClientByUserId() {
        Client client = makeClient();
        User user = new User(client.getId());
        BeanUtils.copyProperties(client, user);

        clientRepository.save(client);
        userRepository.save(user);

        User retrievedUser = userRepository.findById(user.getId()).orElse(null);
        Client retrievedClient = clientRepository.findById(retrievedUser.getId()).orElse(null);

        assertEquals(client, retrievedClient);
    }

    @Test
    public void testFindClientByPublisherId() {
        Client client = makeClient();
        Publisher publisher = makePublisher();

        publisher.addClient(client);
        client.setPublisher(publisher);

        publisherRepository.save(publisher);
        clientRepository.save(client);

        Collection<Client> clients = (Collection<Client>) clientRepository.findByPublisherId(publisher.getId());
        assertEquals(1, clients.size());
    }

    @Test
    public void testFindClientsByPublisherId() {
        Publisher publisher = makePublisher();
        Client client = makeClient();
        Client thirdClient = makeThirdClient();
        Client secondClient =makeSecondClient();

        publisher.addClient(client);
        publisher.addClient(thirdClient);
        publisher.addClient(secondClient);
        client.setPublisher(publisher);
        thirdClient.setPublisher(publisher);
        secondClient.setPublisher(publisher);

        publisherRepository.save(publisher);
        clientRepository.save(client);
        clientRepository.save(thirdClient);
        clientRepository.save(secondClient);

        Collection<Client> clients = (Collection<Client>) clientRepository.findByPublisherId(publisher.getId());

        assertEquals(3, clients.size());
    }

    @Test
    public void testFindAllClients() {
        Client client = makeClient();
        Client secondClient = makeSecondClient();
        Client thirdClient = makeThirdClient();

        clientRepository.save(client);
        clientRepository.save(secondClient);
        clientRepository.save(thirdClient);

        Collection<Client> clients = clientRepository.findAll();

        assertEquals(3, clients.size());
    }

    @Test
    public void testDeleteClientById() {
        Client client = makeClient();
        Product product = makeProduct();
        Order order = makeOrder();

        client.addOrder(order);
        client.addProduct(product);
        product.setOwner(client);
        order.setOwner(client);

        User user = new User(client.getId());
        BeanUtils.copyProperties(client, user);

        userRepository.save(user);
        productRepository.save(product);
        orderRepository.save(order);
        clientRepository.save(client);

        clientRepository.deleteById(client.getId());

        assertNull(clientRepository.findById(client.getHexId()));
    }

    @Test
    public void testDeleteAllClient(){
        clientRepository.deleteAll();

        assertEquals(0, clientRepository.findAll().size());
    }
}