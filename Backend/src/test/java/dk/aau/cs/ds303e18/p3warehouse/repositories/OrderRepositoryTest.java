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

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

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
    public void testFindAllOrders() {
        List<Order> orders = orderRepository.findAll();
        assertThat(orders.size(), is(greaterThanOrEqualTo(0)));
    }

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

        clientOrder.setOrderLines(Collections.singleton(clientOrderLine));
        clientOrder.setOrderLines(Collections.singleton(orderLine));

        publisherOrder.setOrderLines(Collections.singleton(publisherOrderLine));
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
        order.setCity("Serene");
        order.setPhoneNumber("66498726");
        order.setZipCode("5979");
        order.setCountry("Denmark");
        order.setCompany("sports shop");
        order.setContactPerson("Molly");

        return order;
    }


    @Test
    public void testFindInformationOnOrder() {
        Order order = makeOrder();

        orderRepository.save(order);
        Optional<Order> optOrder = orderRepository.findById(order.getId());
        Order retrievedOrder = optOrder.get();

        assertEquals(order.getTitle(), retrievedOrder.getTitle());
        assertEquals(order.getOrderId(), retrievedOrder.getOrderId());
        assertEquals(order.getAddress(), retrievedOrder.getAddress());
        assertEquals(order.getDate(), retrievedOrder.getDate());
        assertEquals(order.getCity(), retrievedOrder.getCity());
        assertEquals(order.getCompany(), retrievedOrder.getCompany());
        assertEquals(order.getContactPerson(), retrievedOrder.getContactPerson());
        assertEquals(order.getCountry(), retrievedOrder.getCountry());
        assertEquals(order.getPhoneNumber(), retrievedOrder.getPhoneNumber());

        System.out.println(order.getCity() + "\n" + order.getCompany() + "\n" + order.getContactPerson()
        + "\n" + order.getCountry() + "\n" + order.getPhoneNumber());

        orderRepository.delete(order);
    }

    @Test
    public void testFindOrderLine() {
        Product product = makeProduct();

        OrderLine orderLine = new OrderLine(product, 25);

        Order order = makeOrder();
        order.setOrderLines(Collections.singleton(orderLine));

        orderRepository.save(order);

        Optional<Order> optOrder = orderRepository.findById(order.getId());
        Order retrievedOrder = optOrder.get();

        assertNotNull(retrievedOrder.getOrderLines());
        assertEquals(1, retrievedOrder.getOrderLines().size());
    }

    @Test
    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }
}
