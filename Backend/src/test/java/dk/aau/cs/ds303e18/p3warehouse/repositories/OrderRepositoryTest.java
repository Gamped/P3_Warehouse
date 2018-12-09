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
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
public class OrderRepositoryTest {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ProductRepository productRepository;

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
    public void deletAllEntries(){

        orderRepository.deleteAll();
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

        Product flyerProduct = new Product(new ObjectId());
        product.setProductName("Flyer for Advertisement");
        product.setQuantity(500);
        product.setProductId("1002");

        ObjectId orderId = new ObjectId();
        Order order = new Order(orderId);
        order.setOwner(client);
        order.setDate(new Date());



        try {
            order.withNewOrderLine(product, 5);
            order.withNewOrderLine(flyerProduct, 300);
            order.setTitle("Testorder");
        }catch(InvalidQuantityException e ){

        }
        clientRepository.save(client);
        productRepository.save(product);
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
    public void testFindInformationOnOrder() {
        ObjectId id = new ObjectId();
        Order order = new Order(id);

        order.setDate(new Date());
        order.setAddress("hvv 33");
        order.setTitle("flyers");
        order.setOrderId("3244232");

        orderRepository.save(order);
        Optional<Order> optOrder = orderRepository.findById(order.getId());
        Order retrievedOrder = optOrder.get();

        assertEquals(order.getTitle(), retrievedOrder.getTitle());
        assertEquals(order.getOrderId(), retrievedOrder.getOrderId());
        assertEquals(order.getAddress(), retrievedOrder.getAddress());
        assertEquals(order.getDate(), retrievedOrder.getDate());

        orderRepository.delete(order);
    }

    @Test
    public void testFindOrderLine() {
        ObjectId orderId = new ObjectId();
        ObjectId productId = new ObjectId();

        Order order = new Order(orderId);
        Product product = new Product(productId);

        product.setQuantity(562);
        product.setProductId("25426426");
        product.setProductName("notes");

        OrderLine orderLine = new OrderLine(product, 265);
        order.setOrderLines(Collections.singleton(orderLine));

        orderRepository.save(order);
        productRepository.save(product);
        Optional<Order> optOrder = orderRepository.findById(order.getId());
        Order retrievedOrder = optOrder.get();

        assertEquals(order.getOrderLines(), retrievedOrder.getOrderLines());

        orderRepository.delete(order);
        productRepository.delete(product);

    }
}
