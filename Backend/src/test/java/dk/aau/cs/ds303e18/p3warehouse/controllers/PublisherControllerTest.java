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
    public void testFindAllClientProduct() {
        ObjectId productId = new ObjectId();
        ObjectId newProductId = new ObjectId();
        ObjectId product_3 = new ObjectId();
        ObjectId product_4 = new ObjectId();
        ObjectId publisherId = new ObjectId();
        ObjectId clientId = new ObjectId();
        ObjectId malClient = new ObjectId();
        ObjectId løbClient = new ObjectId();

        Product newProduct = new Product(newProductId);
        Product product = new Product(productId);
        Product secondProduct = new Product(product_3);
        Product thirdProduct = new Product(product_4);

        Publisher publisher = new Publisher(publisherId);

        Client client = new Client(clientId);
        Client secondClient= new Client(malClient);
        Client thirdClient = new Client(løbClient);

        product.setOwner(publisher);
        secondProduct.setOwner(secondClient);
        thirdProduct.setOwner(thirdClient);
        newProduct.setOwner(client);

        client.addProduct(newProduct);
        secondClient.addProduct(secondProduct);
        thirdClient.addProduct(thirdProduct);

        publisher.addClient(client);
        publisher.addClient(secondClient);
        publisher.addClient(thirdClient);

        List<Product> productList = new LinkedList<>();
        productList.add(product);
        productList.add(newProduct);
        productList.add(secondProduct);
        productList.add(thirdProduct);

        System.out.println(product);
        when(productRepository.findByOwner(publisher)).thenReturn(productList);

        Iterable<Product> products = publisherController.findAllClientsProducts(publisher);
        verify(productRepository).findByOwner(publisher);
        assertNotNull(products);
        assertEquals(product, products);
    }

    @Test
    public void testFindAllClientOrders() {
        ObjectId id = new ObjectId();
        ObjectId orderId = new ObjectId();
        ObjectId clientId = new ObjectId();
        ObjectId publisherId = new ObjectId();
        ObjectId objectId = new ObjectId();
        Order order = new Order(id);
        Order secondOrder = new Order(orderId);
        Client client = new Client(clientId);
        Client secondClient = new Client(objectId);
        Publisher publisher = new Publisher(publisherId);

        client.addOrder(order);
        secondClient.addOrder(secondOrder);
        order.setOwner(client);
        secondOrder.setOwner(secondClient);
        publisher.addClient(secondClient);
        publisher.addClient(client);
        client.setPublisher(publisher);
        List<Order> orderCollection = new LinkedList<>();
        orderCollection.add(order);
        orderCollection.add(secondOrder);
        System.out.println(orderCollection);
        when(orderRepository.findByOwner(publisher.getClientStream().iterator().next())).thenReturn(orderCollection);

        Iterable<Order> orders = publisherController.findAllClientOrders(publisher);
        verify(orderRepository).findByOwner(publisher.getClientStream().iterator().next());

        assertNotNull(orders);
        assertEquals(orderCollection, orders);
    }

    @Test
    public void testFindAllPublishers() {
        ObjectId id = new ObjectId();
        ObjectId publisherId = new ObjectId();
        Publisher publisher = new Publisher(publisherId);
        Publisher secondPublisher = new Publisher(id);
        publisher.setPublisherName("a");
        publisher.setNickName("ave");
        publisher.setUserName("monkey");
        secondPublisher.setPublisherName("b");
        secondPublisher.setNickName("bve");
        secondPublisher.setUserName("bosso");
        List<Publisher> publisherList = new LinkedList<>();
        publisherList.add(publisher);
        publisherList.add(secondPublisher);

        when(publisherRepository.findAll()).thenReturn(publisherList);

        Iterable<Publisher> publishers = publisherController.findAll();

        verify(publisherRepository).findAll();
        System.out.println(publishers);
        assertEquals(publisherList, publishers);
    }

    @Test
    public void findOneClientProducts() {
        ObjectId clientId = new ObjectId();
        ObjectId productId = new ObjectId();
        ObjectId newProductId = new ObjectId();

        Client client = new Client(clientId);

        Product newProduct = new Product(newProductId);
        Product product = new Product(productId);

        newProduct.setOwner(client);
        product.setOwner(client);

        client.addProduct(product);
        client.addProduct(newProduct);

        List<Product> productCollection = new LinkedList<>();
        productCollection.add(product);
        productCollection.add(newProduct);

        System.out.println(product);
        when(productRepository.findByOwner(client)).thenReturn(productCollection);

        Iterable<Product> products = publisherController.findProductsByClientId(String.valueOf(client.getHexId()));
        verify(productRepository).findByOwner(client);
        assertNotNull(products);
        assertEquals(product, products);
    }

    @Test
    public void findOrderByUserId() {
        ObjectId orderId = new ObjectId();
        ObjectId clientId = new ObjectId();
        ObjectId secondOrderId = new ObjectId();
        Client client = new Client(clientId);
        Order order = new Order(orderId);
        Order secondOrder = new Order(secondOrderId);
        order.setOwner(client);
        List<Order> orderList = new LinkedList<>();
        orderList.add(order);
        orderList.add(secondOrder);

        when(orderRepository.findByOwner(client)).thenReturn(orderList);

        Iterable<Order> orders = publisherController.findOneClientOrders(client);
        verify(orderRepository).findByOwner(client);
        List<Order> secondOrderList = Collections.singletonList(order);
        assertNotNull(orders);
        assertEquals(secondOrderList, orders);
    }
}
