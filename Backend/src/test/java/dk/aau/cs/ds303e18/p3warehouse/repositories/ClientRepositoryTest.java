package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.models.orders.OrderLine;
import dk.aau.cs.ds303e18.p3warehouse.models.users.*;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import org.bson.types.ObjectId;
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
        product.setOwner(client);
        secondProduct.setOwner(client);

        productRepository.save(product);
        productRepository.save(secondProduct);
        clientRepository.save(client);

        Client retrievedClient = clientRepository.findById(client.getHexId());
        Stream<Product> productStream = retrievedClient.getProductStream();
        List<Product> productList = productStream.collect(Collectors.toList());

        assertNotNull(productList);
        assertEquals(2, productList.size());
        assertTrue(productList.contains(product));
        assertTrue(productList.contains(secondProduct));
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
        ObjectId productId = new ObjectId();
        Order order = makeOrder();
        ObjectId id = new ObjectId();
        Client client = makeClient();

        Product secondProduct = new Product(productId);
        secondProduct.setProductName("Running news");
        secondProduct.setProductId("sefe5684646");
        secondProduct.setQuantity(20);

        Order secondOrder = new Order(id);
        secondOrder.setTitle("secondorder");
        secondOrder.setDate(new Date());
        secondOrder.setAddress("mour 4");
        secondOrder.setOrderId("35223645654ddd");
        secondOrder.setCity("Serene");
        secondOrder.setPhoneNumber("66498726");
        secondOrder.setZipCode("5979");
        secondOrder.setCountry("Denmark");
        secondOrder.setCompany("sports shop");
        secondOrder.setContactPerson("Molly");

        OrderLine orderLine = new OrderLine(product, 250);
        OrderLine secondOrderLine = new OrderLine(secondProduct, 15);
        order.setOrderLines(Collections.singleton(orderLine));
        secondOrder.setOrderLines(Collections.singleton(secondOrderLine));

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
        assertNotNull(clients);
        assertEquals(1, clients.size());
    }

    @Test
    public void testFindClientsByPublisherId() {
        Publisher publisher = makePublisher();
        Client client = makeClient();
        ObjectId secondId = new ObjectId();
        Client thirdClient = new Client(secondId);
        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setNickName("Karen");
        contactInformation.setEmail("client@kare.rr");
        contactInformation.setAddress("fffavej 2");
        contactInformation.setPhoneNumber("298522654");
        contactInformation.setZipCode("9825");
        contactInformation.setCity("Hadsten");

        thirdClient.setContactInformation(contactInformation);
        thirdClient.setUserType(UserType.CLIENT);
        thirdClient.setUserName("secondclient");
        thirdClient.setPassword("esfesgrs");

        ObjectId id = new ObjectId();
        Client secondClient = new Client(id);
        ContactInformation secondContactInformation = new ContactInformation();

        secondContactInformation.setNickName("Gose");
        secondContactInformation.setEmail("publisher@fef.rr");
        secondContactInformation.setAddress("revej 4");
        secondContactInformation.setPhoneNumber("1568433546");
        secondContactInformation.setZipCode("5979");
        secondContactInformation.setCity("Rye");
        secondClient.setUserType(UserType.CLIENT);
        secondClient.setUserName("thirdclient");
        secondClient.setPassword("fesgr4546");
        secondClient.setContactInformation(secondContactInformation);

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
        assertTrue(clients.contains(client));
        assertTrue(clients.contains(secondClient));
        assertTrue(clients.contains(thirdClient));

    }

    @Test
    public void testFindAllClients() {
        Client client = makeClient();
        ObjectId id = new ObjectId();
        Client secondClient = new Client(id);

        ObjectId secondId = new ObjectId();
        Client thirdClient = new Client(secondId);
        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setNickName("Karen");
        contactInformation.setEmail("client@kare.rr");
        contactInformation.setAddress("fffavej 2");
        contactInformation.setPhoneNumber("298522654");
        contactInformation.setZipCode("9825");
        contactInformation.setCity("Hadsten");

        thirdClient.setContactInformation(contactInformation);
        thirdClient.setUserType(UserType.CLIENT);
        thirdClient.setUserName("secondclient");
        thirdClient.setPassword("esfesgrs");

        ContactInformation secondContactInformation = new ContactInformation();

        secondContactInformation.setNickName("Gose");
        secondContactInformation.setEmail("publisher@fef.rr");
        secondContactInformation.setAddress("revej 4");
        secondContactInformation.setPhoneNumber("1568433546");
        secondContactInformation.setZipCode("5979");
        secondContactInformation.setCity("Rye");
        secondClient.setUserType(UserType.CLIENT);
        secondClient.setUserName("thirdclient");
        secondClient.setPassword("fesgr4546");
        secondClient.setContactInformation(secondContactInformation);

        clientRepository.save(client);
        clientRepository.save(secondClient);
        clientRepository.save(thirdClient);

        Collection<Client> clients = clientRepository.findAll();

        assertEquals(3, clients.size());
        assertTrue(clients.contains(client));
        assertTrue(clients.contains(secondClient));
        assertTrue(clients.contains(thirdClient));
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
