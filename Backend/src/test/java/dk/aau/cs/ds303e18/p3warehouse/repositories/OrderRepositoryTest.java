package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.CustomException.InvalidQuantityException;
import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    public void dbRefTest(){
        Client client = new Client(new ObjectId());
        client.setUserName("simonusr");
        client.setNickName("Simon A/S");

        ObjectId orderId = new ObjectId();
        Order order = new Order(orderId);

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

        order.setOwner(client);
        try {
            order.withNewOrderLine(product, 5);
            order.withNewOrderLine(flyerProduct, 300);
            order.setTitle("Testorder");
        }catch(InvalidQuantityException e ){

        }
        clientRepository.save(client);
        productRepository.save(product);
        orderRepository.save(order);


        //order = null;
        //client = null;
        //product = null;

        //Query order and see if mongo automatically refs our products and clients
        Order queryedOrder = orderRepository.findById(orderId).orElse(null);
        Client queryedClient = clientRepository.findById(queryedOrder.getOwner().getHexId()).orElse(null);

        assert(queryedOrder != null && queryedClient.getHexId().equals(client.getHexId()));
        /*
        NB: Ved ikke om Java bare refererer til java-objekterne uden om db, eller om DB faktisk henter det til os.
        Får vi problemer med dbRefs senere, er det nok dette vi først skal tjekke.
        Håber en venlig sjæl kan huske det, for jeg kan nok ikke.
        */

    }
}
