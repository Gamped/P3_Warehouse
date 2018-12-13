package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
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

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void testFindAllPublishers(){
        List<Publisher> publishers = publisherRepository.findAll();
        assertThat(publishers.size(), is(greaterThanOrEqualTo(0)));
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
        Product secondProduct = makeProduct();

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
        Order secondOrder = makeOrder();
        Order thirdOrder = makeOrder();
        Publisher publisher = makePublisher();

        publisher.addOrder(order);
        publisher.addOrder(secondOrder);
        publisher.addOrder(thirdOrder);

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
    public void testFindInformation(){
        Publisher publisher = makePublisher();

        publisherRepository.save(publisher);

        Optional<Publisher> optPublisher = publisherRepository.findById(publisher.getHexId());
        Publisher retrievedPublisher = optPublisher.get();

        assertEquals(publisher.getUserName(), retrievedPublisher.getUserName());
        assertEquals(publisher.getPassword(), retrievedPublisher.getPassword());
        assertEquals(publisher.getContactInformation(), retrievedPublisher.getContactInformation());
        assertEquals(publisher.getUserType(), retrievedPublisher.getUserType());
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
