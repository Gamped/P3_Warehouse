package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.CustomException.InvalidQuantityException;
import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.models.orders.OrderLine;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.users.ContactInformation;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Publisher;
import dk.aau.cs.ds303e18.p3warehouse.models.users.UserType;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockClientData.makeClient;
import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockOrderData.makeOrder;
import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockOrderData.makeSecondOrder;
import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockProductData.makeProduct;
import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockProductData.makeSecondProduct;
import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockPublisherData.makePublisher;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class OrderRepositoryTest {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    PublisherRepository publisherRepository;


    @Test
    public void findByIdTest(){
        Order order = new Order(new ObjectId());
        order.setTitle("ID test 1");
        System.out.println("Object ID: " + order.getId());
        orderRepository.save(order);
    }

    @Test
    public void checkDatabase() {
        ObjectId publisherId = new ObjectId();
        ObjectId clientId = new ObjectId();
        ObjectId productId = new ObjectId();
        ObjectId publisherProductId = new ObjectId();
        ObjectId clientProductId = new ObjectId();
        ObjectId publisherOrderId = new ObjectId();
        ObjectId clientOrderId = new ObjectId();

        Publisher publisher = new Publisher(publisherId);
        Client client = new Client(clientId);
        ContactInformation publisherContact = new ContactInformation();
        ContactInformation clientContact = new ContactInformation();
        publisherContact.setNickName("Gyldendal");
        publisherContact.setEmail("123@123.com");
        clientContact.setEmail("123@123 .com");
        publisherContact.setPhoneNumber("12345678");
        clientContact.setPhoneNumber("12345678");
        clientContact.setNickName("Aalborg Zoo");

        publisher.setContactInformation(publisherContact);
        client.setContactInformation(clientContact);


        Product product = new Product(productId);
        Product clientProduct = new Product(clientProductId);
        Product publisherProduct = new Product(publisherProductId);

        product.setProductName("Cycling news");
        product.setQuantity(10);
        product.setProductId("62995962");

        clientProduct.setProductName("Running news");
        clientProduct.setProductId("sefe5684646");
        clientProduct.setQuantity(20);

        publisherProduct.setProductName("Car magazine");
        publisherProduct.setQuantity(35);
        publisherProduct.setProductId("561313");

        clientProduct.setOwner(client);
        publisherProduct.setOwner(publisher);
        Order clientOrder = new Order(clientOrderId);
        Order publisherOrder = new Order(publisherOrderId);

        OrderLine clientOrderLine = new OrderLine(product, 4);
        OrderLine orderLine = new OrderLine(clientProduct, 10);
        OrderLine publisherOrderLine = new OrderLine(publisherProduct, 5);

        clientOrder.setTitle("clientorder");
        publisherOrder.setTitle("publisherorder");
        clientOrder.setOrderId("100");
        publisherOrder.setOrderId("123");
        clientOrder.setDate(new Date());
        publisherOrder.setDate(new Date());

        ArrayList<OrderLine> a = new ArrayList<>();
        ArrayList<OrderLine> b = new ArrayList<>();
        ArrayList<OrderLine> c = new ArrayList<>();

        a.add(orderLine);
        b.add(clientOrderLine);
        c.add(publisherOrderLine);

        clientOrder.setOrderLines(b);
        clientOrder.setOrderLines(a);

        publisherOrder.setOrderLines(c);
        publisher.setUserName("Publisher");
        publisher.setPassword("esfegr8433");
        publisher.setUserType(UserType.PUBLISHER);

        client.setUserName("Client");
        client.setUserType(UserType.CLIENT);

        client.addProduct(product);
        client.addProduct(clientProduct);
        client.addOrder(clientOrder);

        publisher.addProduct(publisherProduct);
        publisher.addOrder(publisherOrder);
        publisher.addClient(client);

        productRepository.save(product);
        productRepository.save(clientProduct);
        productRepository.save(publisherProduct);
        orderRepository.save(clientOrder);
        orderRepository.save(publisherOrder);
        clientRepository.save(client);
        publisherRepository.save(publisher);
    }

    @Before
    public void deleteAllEntries(){

        orderRepository.deleteAll();
        productRepository.deleteAll();
        clientRepository.deleteAll();
        publisherRepository.deleteAll();
    }

    @Test
    public void dbRefTest(){

        ObjectId pubId = new ObjectId();
        Publisher publisher = new Publisher(pubId);
        publisher.setUserName("Peter 4N");
        publisher.setUserType(UserType.PUBLISHER);
        ContactInformation pubContact = new ContactInformation();
        publisher.setContactInformation(pubContact);
        pubContact.setNickName("Publisher John");


        Client client = new Client(new ObjectId());
        client.setUserName("simon123");
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setNickName("Simon A/S");

        client.setPublisher(publisher);
        client.setUserType(UserType.CLIENT);

        Product product = new Product(new ObjectId());
        product.setProductName("Computer");
        product.setQuantity(10);
        product.setProductId("1001");
        product.setOwner(client);

        Product flyerProduct = new Product(new ObjectId());
        product.setProductName("Flyer for Advertisement");
        product.setQuantity(500);
        product.setProductId("1002");
        flyerProduct.setOwner(client);

        ObjectId orderId = new ObjectId();
        Order order = new Order(orderId);
        order.setOwner(client);
        order.setDate(new Date());

        client.addProduct(product);
        client.addProduct(flyerProduct);
        client.addOrder(order);

        try {
            order.withNewOrderLine(product, 5);
            order.withNewOrderLine(flyerProduct, 300);
            order.setTitle("Testorder");
        }catch(InvalidQuantityException e ){

        }

        publisherRepository.save(publisher);
        clientRepository.save(client);
        productRepository.save(product);
        productRepository.save(flyerProduct);
        orderRepository.save(order);



        //Query order and see if mongo automatically refs our products and clients
        Order queryedOrder = orderRepository.findById(orderId).orElse(null);
        Client queryedClient = clientRepository.findById(queryedOrder.getOwner().getHexId());
        assert(queryedOrder != null && queryedClient.getHexId().equals(client.getHexId()));

      //  clientRepository.delete(client);
      //  productRepository.delete(product);
     //   orderRepository.delete(order);


    }

    @Test
    public void testFindOrderById() {
        Order order = makeOrder();

        orderRepository.save(order);
        Order retrievedOrder = orderRepository.findById(order.getId()).orElse(null);
        assertEquals(order.getId(), retrievedOrder.getId());
    }

    @Test
    public void testFindInformationOnOrder() {
        Order order = makeOrder();

        orderRepository.save(order);
        Optional<Order> optOrder = orderRepository.findById(order.getId());
        Order retrievedOrder = optOrder.get();

        assertEquals(order.getTitle(), retrievedOrder.getTitle());
    }

    @Test
    public void testOrderOwner() {
        Order order = makeOrder();
        Publisher publisher = makePublisher();

        publisher.addOrder(order);
        order.setOwner(publisher);

        publisherRepository.save(publisher);
        orderRepository.save(order);

        Order retrieveddOrder = orderRepository.findById(order.getId()).orElse(null);
        assertEquals(publisher, retrieveddOrder.getOwner());
    }


    @Test
    public void testFindOrderLine() {
        Product product = makeProduct();
        Order order = makeOrder();

        OrderLine orderLine = new OrderLine(product, 25);
        OrderLine secondOrderLine = new OrderLine(product, 250);

        ArrayList<OrderLine> orderLines = new ArrayList<>();
        orderLines.add(orderLine);
        orderLines.add(secondOrderLine);
        order.setOrderLines(orderLines);

        orderRepository.save(order);

        Optional<Order> optOrder = orderRepository.findById(order.getId());
        Order retrievedOrder = optOrder.get();

        assertEquals(2, retrievedOrder.getOrderLines().size());
    }

    @Test
    public void testAddProductsBackToStock() {
        Product product = makeProduct();
        Order order = makeOrder();

        OrderLine orderLine = new OrderLine(product, 25);
        ArrayList<OrderLine> a = new ArrayList<>();
        a.add(orderLine);
        order.setOrderLines(a);

        order.addProductsBackToStock();
        assertEquals(45, product.getQuantity());
    }

    @Test
    public void testSubtractProductsFromStock() {
        Product product = makeProduct();
        Order order = makeOrder();

        OrderLine orderLine = new OrderLine(product, 15);
        ArrayList<OrderLine> a = new ArrayList<>();
        a.add(orderLine);
        order.setOrderLines(a);

        try {
            order.subtractProductsFromStock();
        } catch (InvalidQuantityException e) {

        }

        assertEquals(5, product.getQuantity());
    }

    @Test
    public void testFindAllOrders() {
        Order order =  makeOrder();
        Order secondOrder = makeSecondOrder();
        Product product = makeProduct();
        Product secondProduct = makeSecondProduct();
        Publisher publisher = makePublisher();
        Client client = makeClient();

        OrderLine orderLine = new OrderLine(product, 250);
        OrderLine secondOrderLine = new OrderLine(secondProduct, 20);

        ArrayList<OrderLine> a = new ArrayList<>();
        ArrayList<OrderLine> b = new ArrayList<>();

        a.add(orderLine);
        b.add(secondOrderLine);

        order.setOrderLines(a);
        secondOrder.setOrderLines(b);


        publisher.addOrder(order);
        publisher.addProduct(product);
        client.addProduct(secondProduct);
        client.addOrder(secondOrder);

        orderRepository.save(order);
        orderRepository.save(secondOrder);
        productRepository.save(product);
        productRepository.save(secondProduct);
        publisherRepository.save(publisher);
        clientRepository.save(client);

        Collection<Order> orders = orderRepository.findAll();
        assertEquals(2, orders.size());
    }

    @Test
    public void testDeleteOrder() {
        Order order = makeOrder();
        Product product = makeProduct();
        Client client = makeClient();
        client.addOrder(order);

        OrderLine orderLine = new OrderLine(product, 250);
        ArrayList<OrderLine> a = new ArrayList<>();
        a.add(orderLine);
        order.setOrderLines(a);
        order.setOwner(client);

        orderRepository.save(order);
        clientRepository.save(client);
        orderRepository.delete(order);

        assertFalse(orderRepository.existsById(order.getId()));
    }

    @Test
    public void deleteAllOrders() {
        orderRepository.deleteAll();

        assertEquals(0, orderRepository.findAll().size());
    }
}
