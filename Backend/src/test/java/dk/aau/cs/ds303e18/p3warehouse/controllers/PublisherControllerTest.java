package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestPublisherModel;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Publisher;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import dk.aau.cs.ds303e18.p3warehouse.repositories.OrderRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ProductRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.PublisherRepository;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PublisherControllerTest {

    @InjectMocks
    PublisherController publisherController;

    @Mock
    PublisherRepository publisherRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    OrderRepository orderRepository;

    @Test
    public void publisherControllerLoads() throws Exception {
        assertThat(publisherController).isNotNull();
    }

    @Before
    public void start() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindPublisherById() {
        ObjectId id = new ObjectId();
        Publisher publisher = new Publisher(id);

        when(publisherRepository.findById(id)).thenReturn(Optional.of(publisher));

        Optional<Publisher> optPublisher =  publisherController.findById(String.valueOf(id));
        Publisher retrievedPublisher = optPublisher.get();
        verify(publisherRepository).findById(id);
        assertEquals(publisher.getId(), retrievedPublisher.getId());
    }

    @Test
    public void testUpdatePublisher() {
        ObjectId id = new ObjectId();
        Publisher publisher = new Publisher(id);
        RestPublisherModel restPublisher = new RestPublisherModel();
        restPublisher.setCompanyName("aCompany");
        restPublisher.setPublisherName("karen");

        when(publisherRepository.findById(id)).thenReturn(Optional.of(publisher));

        String updatePublisher = publisherController.update(publisher.getHexId(), restPublisher);
        verify(publisherRepository).findById(id);
        String publisherString = publisher.toString();

        assertNotSame(publisherString, updatePublisher);
    }

    @Test
    public void testDeletePublisherById() {
        ObjectId id = new ObjectId();
        Publisher publisher = new Publisher(id);

        when(publisherRepository.findById(id)).thenReturn(Optional.of(publisher));

        publisherController.delete(publisher.getHexId());
        verify(publisherRepository).deleteById(id);

        List<Publisher> publishers = new ArrayList<>();
        Assert.assertEquals(publishers, publisherRepository.findAll());
    }

    @Test
    public void testFindOneClientProduct() {
        ObjectId productId = new ObjectId();
        ObjectId clientId = new ObjectId();
        ObjectId id = new ObjectId();
        Product product = new Product(productId);
        Publisher publisher = new Publisher(clientId);
        Client client = new Client(id);
        product.setOwner(publisher);
        client.addProduct(product);
        publisher.addClient(client);
        //Iterable<Client> clients = publisher.getClients();
        //System.out.println(clients);

        when(productRepository.findAllById(Collections.singleton(client.getId()))).thenReturn(Collections.singleton(product));
        Iterable<Product> products = publisherController.findAllClientsProducts();
        verify(productRepository).findAllById(Collections.singleton(client.getId()));
        assertNotNull(products);
        assertEquals(product, products);
    }

    @Test
    public void testFindOrderByClient() {
        ObjectId id = new ObjectId();
        ObjectId orderId = new ObjectId();
        ObjectId clientId = new ObjectId();
        Order order = new Order(id);
        Order order1 = new Order(orderId);
        Client client = new Client(clientId);

        client.addOrder(order);
        client.addOrder(order1);

        /*Collection<Order> orderCollection = null;
        orderCollection.add(order);
        orderCollection.add(order1);*/

        when(orderRepository.findAllById(Collections.singleton(client.getId()))).thenReturn(Collections.singleton(order));

        Iterable<Order> orders = publisherController.findOneClientOrders(String.valueOf(clientId));
        verify(orderRepository).findAllById(Collections.singleton(client.getId()));

        assertNotNull(orders);
        //assertEquals(order.getId(), orders);
    }

    @Test
    public void findOneClientProducts() {
        ObjectId clientId = new ObjectId();
        ObjectId productId = new ObjectId();
        Client client = new Client(clientId);
        Product product = new Product(productId);
        product.setOwner(client);
        client.addProduct(product);

        when(productRepository.findByOwner(client)).thenReturn(Collections.singleton(product));

        Iterable<Product> products = publisherController.findProductsByClientId(String.valueOf(clientId));
        //verify(productRepository).findByOwner(client);
        assertNotNull(products);
        assertEquals(product, products);
    }

    @Test
    public void findOrderByUserId() {
        ObjectId orderId = new ObjectId();
        ObjectId clientId = new ObjectId();
        Client client = new Client(clientId);
        Order order = new Order(orderId);
        order.setOwner(client);

        when(orderRepository.findByOwner(client)).thenReturn(Collections.singleton(order));

        Iterable<Order> orders = publisherController.findOneClientOrders(String.valueOf(orderId));
        //verify(orderRepository).findByOwner(client);
        assertNotNull(orders);
        assertEquals(order, orders);
    }
}
