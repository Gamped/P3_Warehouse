package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.controllers.PublisherController;
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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;

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

    @Test
    public void testFindAllPublishers(){
        List<Publisher> publishers = publisherRepository.findAll();
        assertThat(publishers.size(), is(greaterThanOrEqualTo(1)));
    }

    @Test
    public void savePublisher(){
        ObjectId idP = new ObjectId();
        Publisher publisher = new Publisher(idP);
        publisherRepository.save(publisher);
        System.out.println(publisher.getHexId());
        Optional<Publisher> optionalPublisher = publisherRepository.findById(publisher.getHexId());
        Publisher retrievedPublisher = optionalPublisher.get();
        Assert.assertEquals(publisher.getHexId(), retrievedPublisher.getHexId());
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
        Assert.assertEquals(publisher.getNumberOfClients(),2);
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


    }

    @Test
    public void publisherProducts(){
        ObjectId productId = new ObjectId();
        ObjectId productNewId = new ObjectId();
        ObjectId publisherId = new ObjectId();

        Product product = new Product(productId);
        Product newProduct = new Product(productNewId);
        Publisher publisher = new Publisher(publisherId);

        publisher.addProduct(product);
        publisher.addProduct(newProduct);

        publisherRepository.save(publisher);
        productRepository.save(product);
        productRepository.save(newProduct);

        Optional<Publisher> optPublisher = publisherRepository.findById(publisher.getId());
        Publisher retrievedPublisher = optPublisher.get();
        Stream<Product> products = retrievedPublisher.getProductStream();

        Assert.assertEquals(2, products.collect(Collectors.toSet()).size());
        productRepository.deleteAll();
    }

    @Test
    public void testPublisherFindClientProduct(){
        ObjectId id = new ObjectId();
        ObjectId clientId = new ObjectId();
        ObjectId productId = new ObjectId();
        ObjectId objectId = new ObjectId();
        ObjectId publisherId = new ObjectId();

        Product product = new Product(productId);
        Product secondProduct = new Product(objectId);
        Client client = new Client(clientId);
        Client secondClient = new Client(id);
        Publisher publisher = new Publisher(publisherId);

        client.addProduct(product);
        secondClient.addProduct(secondProduct);
        client.addProduct(secondProduct);
        publisher.addClient(client);
        publisher.addClient(secondClient);

        productRepository.save(product);
        productRepository.save(secondProduct);
        clientRepository.save(client);
        clientRepository.save(secondClient);
        publisherRepository.save(publisher);

        Optional<Publisher> optPublisher = publisherRepository.findById(publisher.getHexId());
        Publisher retrievedPublisher = optPublisher.get();
        Stream<Client> clientStream = retrievedPublisher.getClientStream();

        System.out.println(publisher.getClientStream());

        Stream<Product> productStream =
                clientStream.iterator().next().getProductStream();

        Assert.assertEquals(product, productStream.collect(Collectors.toSet()));
    }

    @Test
    public void testPublisherOrder(){
        ObjectId id = new ObjectId();
        ObjectId objectId = new ObjectId();
        ObjectId orderId = new ObjectId();
        ObjectId publisherId = new ObjectId();

        Order order = new Order(id);
        Order secondOrder = new Order(objectId);
        Order thirdOrder = new Order(orderId);
        Publisher publisher = new Publisher(publisherId);

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

        Assert.assertEquals(3, orderStream.collect(Collectors.toSet()).size());
    }

    @Test
    public void testFindInformation(){
        ObjectId id = new ObjectId();
        Publisher publisher = new Publisher(id);
        ContactInformation contactInformation = new ContactInformation();

        publisher.setUserName("gomokko");
        publisher.setUserType(UserType.PUBLISHER);
        publisher.setPassword("1234");
        contactInformation.setEmail("fill@Go.sf");
        contactInformation.setAddress("husegade 8");
        contactInformation.setPhoneNumber("262565656");
        contactInformation.setZipCode("6468");
        publisher.setContactInformation(contactInformation);

        publisherRepository.save(publisher);

        Optional<Publisher> optPublisher = publisherRepository.findById(publisher.getHexId());
        Publisher retrievedPublisher = optPublisher.get();

        Assert.assertEquals(publisher.getUserName(), retrievedPublisher.getUserName());
        Assert.assertEquals(publisher.getPassword(), retrievedPublisher.getPassword());
        Assert.assertEquals(publisher.getContactInformation(), retrievedPublisher.getContactInformation());
        Assert.assertEquals(publisher.getUserType(), retrievedPublisher.getUserType());
    }

    @Test
    public void testDeletePublisherById(){
        publisherRepository.deleteAll();

        Assert.assertEquals(0, publisherRepository.findAll().size());
    }
}
