package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.CustomException.InvalidQuantityException;
import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Publisher;
import dk.aau.cs.ds303e18.p3warehouse.models.users.UserType;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

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
    public void findByIdTest(){
        Order order = new Order(new ObjectId());
        order.setTitle("ID test 1");
        System.out.println("Object ID: " + order.getId());
        orderRepository.save(order);
    }

    @Test
    public void deletAllEntries(){

        //orderRepository.deleteAll();
    }

    @Test
    public void dbRefTest(){

        ObjectId pubId = new ObjectId();
        Publisher publisher = new Publisher(pubId);
        publisher.setUserName("Peter 4N");
        publisher.setUserType(UserType.PUBLISHER);


        Client client = new Client(new ObjectId());
        client.setUserName("simon123");
        client.getContactInformation().setNickName("Simon A/S");
        client.setPublisher(publisher);
        client.setUserType(UserType.CLIENT);

        Product product = new Product(new ObjectId());
        product.setOwner(client);
        product.setProductName("Computer");
        product.setQuantity(10);
        product.setProductId("1001");

        Product flyerProduct = new Product(new ObjectId());
        product.setOwner(client);
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
}
