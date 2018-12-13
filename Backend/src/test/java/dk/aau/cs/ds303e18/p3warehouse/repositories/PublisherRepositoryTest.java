package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.models.users.*;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public Client makeClient() {
        ObjectId id = new ObjectId();
        Client client = new Client(id);
        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setNickName("Hans");
        contactInformation.setEmail("fes@gr.gdr");
        contactInformation.setPhoneNumber("15334888");
        contactInformation.setAddress("møllevej 4");
        contactInformation.setZipCode("5497");

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
    public void savePublisher(){
        Publisher publisher = makePublisher();
        publisherRepository.save(publisher);
        System.out.println(publisher.getHexId());
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
        ObjectId id = new ObjectId();
        Product secondProduct = new Product(id);
        secondProduct.setProductName("ship");
        secondProduct.setQuantity(65);
        secondProduct.setProductId("3243354654");

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
        ObjectId id = new ObjectId();
        ObjectId objectId = new ObjectId();
        Order order = makeOrder();
        Publisher publisher = makePublisher();
        Order thirdOrder = new Order(objectId);

        thirdOrder.setTitle("thirdorder");
        thirdOrder.setDate(new Date());
        thirdOrder.setAddress("mour 4");
        thirdOrder.setOrderId("35223645654ddd");
        thirdOrder.setCity("Serene");
        thirdOrder.setPhoneNumber("66498726");
        thirdOrder.setZipCode("5979");
        thirdOrder.setCountry("Denmark");
        thirdOrder.setCompany("sports shop");
        thirdOrder.setContactPerson("Molly");

        Order secondOrder = new Order(id);
        order.setTitle("secondorder");
        order.setDate(new Date());
        order.setAddress("foul 23");
        order.setOrderId("242543643678");
        order.setCity("Moonlight");
        order.setPhoneNumber("56846987");
        order.setZipCode("9985");
        order.setCountry("Denmark");
        order.setCompany("music store");
        order.setContactPerson("ole");

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
        ObjectId id = new ObjectId();
        ObjectId objectId = new ObjectId();
        Order order = makeOrder();
        Publisher publisher = makePublisher();
        Order thirdOrder = new Order(objectId);
        Client client = makeClient();

        thirdOrder.setTitle("thirdorder");
        thirdOrder.setDate(new Date());
        thirdOrder.setAddress("mour 4");
        thirdOrder.setOrderId("35223645654ddd");
        thirdOrder.setCity("Serene");
        thirdOrder.setPhoneNumber("66498726");
        thirdOrder.setZipCode("5979");
        thirdOrder.setCountry("Denmark");
        thirdOrder.setCompany("sports shop");
        thirdOrder.setContactPerson("Molly");

        Order secondOrder = new Order(id);
        order.setTitle("secondorder");
        order.setDate(new Date());
        order.setAddress("foul 23");
        order.setOrderId("242543643678");
        order.setCity("Moonlight");
        order.setPhoneNumber("56846987");
        order.setZipCode("9985");
        order.setCountry("Denmark");
        order.setCompany("music store");
        order.setContactPerson("ole");

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
        assertNotNull(orderList);
        assertEquals(3, orderList.size());
    }

    @Test
    public void testFindInformation(){
        Publisher publisher = makePublisher();

        publisherRepository.save(publisher);

        Publisher retrievedPublisher = publisherRepository.findById(publisher.getHexId()).orElse(null);

        assertEquals(publisher.getUserName(), retrievedPublisher.getUserName());
        assertEquals(publisher.getPassword(), retrievedPublisher.getPassword());
        assertEquals(publisher.getContactInformation(), retrievedPublisher.getContactInformation());
        assertEquals(publisher.getUserType(), retrievedPublisher.getUserType());
    }

    @Test
    public void testPublisherClientProducts() {
        Publisher publisher = makePublisher();
        Client client = makeClient();
        ObjectId productId = new ObjectId();
        ObjectId id = new ObjectId();

        Product product = new Product(productId);
        product.setQuantity(400);
        product.setProductName("cycling news");
        product.setProductId("342525");

        Product secondProduct = new Product(id);
        secondProduct.setProductName("ship");
        secondProduct.setQuantity(65);
        secondProduct.setProductId("3243354654");

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

        assertNotNull(productList);
        assertEquals(2, productList.size());
    }

    @Test
    public void testPublisherFindClientsProducts() {
        Publisher publisher = makePublisher();
        Client client = makeClient();
        ObjectId productId = new ObjectId();
        ObjectId id = new ObjectId();
        ObjectId secondProductId = new ObjectId();
        ObjectId secondId = new ObjectId();

        Product product = new Product(productId);
        product.setQuantity(400);
        product.setProductName("cycling news");
        product.setProductId("342525");

        Product secondProduct = new Product(id);
        secondProduct.setProductName("ship");
        secondProduct.setQuantity(65);
        secondProduct.setProductId("3243354654");

        Product thirdProduct = new Product(secondProductId);
        thirdProduct.setProductName("bus");
        thirdProduct.setQuantity(66);
        thirdProduct.setProductId("3r23543645765");

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

        publisher.addClient(client);
        publisher.addClient(thirdClient);
        client.addProduct(product);
        client.addProduct(secondProduct);
        client.setPublisher(publisher);
        thirdClient.addProduct(thirdProduct);
        thirdClient.setPublisher(publisher);

        publisherRepository.save(publisher);
        productRepository.save(product);
        productRepository.save(secondProduct);
        productRepository.save(thirdProduct);
        clientRepository.save(client);
        clientRepository.save(thirdClient);

        Publisher retrievedPublisher = publisherRepository.findById(publisher.getId()).orElse(null);
        Stream<Client> clientStream = retrievedPublisher.getClientStream();
        List<Client> clientList = clientStream.collect(Collectors.toList());
        Stream<Product> productStream = null;
        for (Client aClientList : clientList)
            productStream = aClientList.getProductStream();

        List<Product> productList = productStream.collect(Collectors.toList());

        assertNotNull(productList);
        assertEquals(3, productList.size());
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
        ObjectId publisherId = new ObjectId();
        ObjectId secondPublisherId = new ObjectId();
        Publisher secondPublisher = new Publisher(publisherId);
        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setNickName("music store");
        contactInformation.setEmail("thirdPublisher@ff.cc");
        contactInformation.setPhoneNumber("87525632");
        contactInformation.setAddress("gyldenvej 4");
        contactInformation.setZipCode("2796");
        contactInformation.setCity("Padborg");

        secondPublisher.setUserType(UserType.PUBLISHER);
        secondPublisher.setUserName("thirdpublisher");
        secondPublisher.setPassword("r43tdhytf");
        secondPublisher.setContactInformation(contactInformation);

        Publisher thirdPublisher = new Publisher(secondPublisherId);
        ContactInformation secondContactInformation = new ContactInformation();

        secondContactInformation.setNickName("lej og byg");
        secondContactInformation.setEmail("secondPublisher@ff.cc");
        secondContactInformation.setPhoneNumber("26546235");
        secondContactInformation.setAddress("hale 34");
        secondContactInformation.setZipCode("2695");
        secondContactInformation.setCity("Hals");

        thirdPublisher.setUserType(UserType.PUBLISHER);
        thirdPublisher.setUserName("secondpublisher");
        thirdPublisher.setPassword("erw3ret544");
        thirdPublisher.setContactInformation(secondContactInformation);

        publisherRepository.save(publisher);
        publisherRepository.save(secondPublisher);
        publisherRepository.save(thirdPublisher);

        Collection<Publisher> publishers = publisherRepository.findAll();
        assertNotNull(publishers);
        assertEquals(3, publishers.size());
    }

    @Test
    public void testDeletePublisherById() {
        Publisher publisher = makePublisher();
        Client client = makeClient();
        Product product = makeProduct();
        Order order = makeOrder();
        publisher.addClient(client);
        publisher.addOrder(order);
        publisher.addProduct(product);
        product.setOwner(publisher);
        client.setPublisher(publisher);
        order.setOwner(publisher);

        publisherRepository.save(publisher);
        productRepository.save(product);
        orderRepository.save(order);
        clientRepository.save(client);

        publisherRepository.deleteById(publisher.getHexId());

        Assert.assertNull( publisherRepository.findById(publisher.getId()).orElse(null));
    }

    @Test
    public void testDeleteAllPublishers(){
        publisherRepository.deleteAll();

        assertEquals(0, publisherRepository.findAll().size());
    }
}
