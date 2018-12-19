package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.models.users.*;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockClientData.makeClient;
import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockOrderData.makeOrder;
import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockOrderData.makeSecondOrder;
import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockOrderData.makeThirdOrder;
import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockProductData.makeProduct;
import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockProductData.makeSecondProduct;
import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockPublisherData.makePublisher;
import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockPublisherData.makeSecondPublisher;
import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockPublisherData.makeThirdPublisher;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class PublisherRepositoryTest {
    @Autowired
    PublisherRepository publisherRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void deleteAll() {
        productRepository.deleteAll();
        publisherRepository.deleteAll();
        clientRepository.deleteAll();
        orderRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void savePublisher(){
        Publisher publisher = makePublisher();
        publisherRepository.save(publisher);
        Optional<Publisher> optionalPublisher = publisherRepository.findById(publisher.getHexId());
        Publisher retrievedPublisher = optionalPublisher.get();
        assertEquals(publisher.getHexId(), retrievedPublisher.getHexId());
    }

    @Test
    public void publisherGotClient(){
        ObjectId idP = new ObjectId();
        ObjectId idC = new ObjectId();
        ObjectId idC2 = new ObjectId();
        Publisher publisher = new Publisher(idP);
        Client client = new Client(idC);
        Client client2 = new Client(idC2);
        publisher.addClient(client);
        publisher.addClient(client2);
        assertEquals(publisher.getNumberOfClients(),2);
    }

    @Test
    public void publisherLoadClientForDb(){
        ObjectId idP = new ObjectId();
        ObjectId idC = new ObjectId();
        ObjectId idC2 = new ObjectId();
        Publisher publisher = new Publisher(idP);
        Client client = new Client(idC);
        Client client2 = new Client(idC2);
        publisher.addClient(client);
        publisher.addClient(client2);

        publisherRepository.save(publisher);
        clientRepository.save(client);
        clientRepository.save(client2);

        Publisher retrievedPublisher = publisherRepository.findById(publisher.getId()).orElse(null);
        Stream<Client> clientStream = retrievedPublisher.getClientStream();
        List<Client> clients = clientStream.collect(Collectors.toList());
        assertTrue(clients.contains(client));
        assertTrue(clients.contains(client2));
    }

    @Test
    public void publisherProducts(){
        Publisher publisher = makePublisher();
        Product product = makeProduct();
        Product secondProduct = makeSecondProduct();

        publisher.addProduct(product);
        publisher.addProduct(secondProduct);

        publisherRepository.save(publisher);
        productRepository.save(product);
        productRepository.save(secondProduct);

        Optional<Publisher> optPublisher = publisherRepository.findById(publisher.getId());
        Publisher retrievedPublisher = optPublisher.get();
        Stream<Product> products = retrievedPublisher.getProductStream();
        List<Product> retrievedProducts = products.collect(Collectors.toList());

        assertEquals(2, retrievedProducts.size());
    }

    @Test
    public void testPublisherOrder(){
        Order order = makeOrder();
        Order secondOrder = makeSecondOrder();
        Order thirdOrder = makeThirdOrder();
        Publisher publisher = makePublisher();


        publisher.addOrder(order);
        publisher.addOrder(secondOrder);
        publisher.addOrder(thirdOrder);
        order.setOwner(publisher);
        secondOrder.setOwner(publisher);
        thirdOrder.setOwner(publisher);

        publisherRepository.save(publisher);
        orderRepository.save(order);
        orderRepository.save(secondOrder);
        orderRepository.save(thirdOrder);

        Optional<Publisher> optPublisher = publisherRepository.findById(publisher.getHexId());
        Publisher retrievedPublisher = optPublisher.get();

        Stream<Order> orderStream = retrievedPublisher.getOrderStream();

        assertEquals(3, orderStream.collect(Collectors.toList()).size());
    }

    @Test
    public void testPublisherClientOrders() {
        Order secondOrder = makeSecondOrder();
        Order order = makeOrder();
        Order thirdOrder = makeThirdOrder();
        Publisher publisher = makePublisher();
        Client client = makeClient();

        client.addOrder(order);
        client.addOrder(secondOrder);
        client.addOrder(thirdOrder);
        order.setOwner(client);
        secondOrder.setOwner(client);
        thirdOrder.setOwner(client);
        publisher.addClient(client);
        client.setPublisher(publisher);

        publisherRepository.save(publisher);
        orderRepository.save(order);
        orderRepository.save(secondOrder);
        orderRepository.save(thirdOrder);
        clientRepository.save(client);

        Publisher retrievedPublisher = publisherRepository.findById(publisher.getId()).orElse(null);
        Stream<Client> clientStream = retrievedPublisher.getClientStream();
        List<Client> clientList = clientStream.collect(Collectors.toList());

        Stream<Order> orderStream = null;
        for (Client aClient : clientList) {
            orderStream = aClient.getOrderStream();
        }

        List<Order> orderList = orderStream.collect(Collectors.toList());
        assertEquals(3, orderList.size());
    }

    @Test
    public void testFindInformation(){
        Publisher publisher = makePublisher();

        publisherRepository.save(publisher);

        Publisher retrievedPublisher = publisherRepository.findById(publisher.getHexId()).orElse(null);

        assertEquals(publisher.getPassword(), retrievedPublisher.getPassword());
    }

    @Test
    public void testPublisherClientProducts() {
        Publisher publisher = makePublisher();
        Client client = makeClient();
        Product product = makeProduct();
        Product secondProduct = makeSecondProduct();

        publisher.addClient(client);
        client.addProduct(product);
        client.addProduct(secondProduct);
        client.setPublisher(publisher);

        publisherRepository.save(publisher);
        productRepository.save(product);
        productRepository.save(secondProduct);
        clientRepository.save(client);

        Publisher retrievedPublisher = publisherRepository.findById(publisher.getId()).orElse(null);
        Stream<Client> clientStream = retrievedPublisher.getClientStream();
        List<Client> clientList = clientStream.collect(Collectors.toList());
        Stream<Product> productStream = null;
        for (Client aClientList : clientList) {
            productStream = aClientList.getProductStream();
        }
        List<Product> productList = productStream.collect(Collectors.toList());

        assertEquals(2, productList.size());
    }

    @Test
    public void testFindPublisherByUserId() {
        Publisher publisher = makePublisher();
        User user = new User(publisher.getId());
        user.copyFrom(publisher);

        publisherRepository.save(publisher);
        userRepository.save(user);

        Publisher retrievedPublisher = publisherRepository.findById(user.getId()).orElse(null);
        assertEquals(publisher, retrievedPublisher);
    }

    @Test
    public void testFindAllPublishers() {
        Publisher publisher = makePublisher();
        Publisher secondPublisher = makeSecondPublisher();
        Publisher thirdPublisher = makeThirdPublisher();

        publisherRepository.save(publisher);
        publisherRepository.save(secondPublisher);
        publisherRepository.save(thirdPublisher);

        Collection<Publisher> publishers = publisherRepository.findAll();

        assertEquals(3, publishers.size());
    }

    @Test
    public void testDeletePublisherById() {
        Publisher publisher = makePublisher();

        publisherRepository.save(publisher);

        publisherRepository.deleteById(publisher.getHexId());

        Assert.assertNull( publisherRepository.findById(publisher.getId()).orElse(null));
    }

    @Test
    public void testDeleteAllPublishers(){
        publisherRepository.deleteAll();

        assertEquals(0, publisherRepository.findAll().size());
    }
}
