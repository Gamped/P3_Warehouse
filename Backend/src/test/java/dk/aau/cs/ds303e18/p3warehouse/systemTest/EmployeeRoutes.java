package dk.aau.cs.ds303e18.p3warehouse.systemTest;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.models.orders.OrderLine;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Publisher;
import dk.aau.cs.ds303e18.p3warehouse.models.users.UserType;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ClientRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.OrderRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ProductRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.PublisherRepository;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@RunWith(SpringRunner.class)
@DataMongoTest
public class EmployeeRoutes {

    @Autowired
    PublisherRepository publisherRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void checkDatabase() {
        orderRepository.deleteAll();
        publisherRepository.deleteAll();
        productRepository.deleteAll();
        clientRepository.deleteAll();

        ObjectId publisherId = new ObjectId();
        ObjectId clientId = new ObjectId();
        ObjectId productId = new ObjectId();
        ObjectId publisherProductId = new ObjectId();
        ObjectId clientProductId = new ObjectId();
        ObjectId publisherOrderId = new ObjectId();
        ObjectId clientOrderId = new ObjectId();

        Publisher publisher = new Publisher(publisherId);
        Client client = new Client(clientId);
        Product product = new Product(productId);
        Product clientProduct = new Product(clientProductId);
        Product publisherProduct = new Product(publisherProductId);

        product.setProductName("Cycling news");
        product.setQuantity(10);
        //product.setOwner(client);
        product.setProductId("62995962");

        clientProduct.setProductName("Running news");
        clientProduct.setProductId("sefe5684646");
        //clientProduct.setOwner(client);
        clientProduct.setQuantity(20);

        publisherProduct.setProductName("Car magazine");
        publisherProduct.setQuantity(35);
        //publisherProduct.setOwner(publisher);
        publisherProduct.setProductId("561313");

        Order clientOrder = new Order(clientOrderId);
        Order publisherOrder = new Order(publisherOrderId);

        OrderLine clientOrderLine = new OrderLine(product, 4);
        OrderLine orderLine = new OrderLine(clientProduct, 10);
        OrderLine publisherOrderLine = new OrderLine(publisherProduct, 5);

        clientOrder.setTitle("clientorder");
        clientOrder.setOrderLines(Collections.singleton(clientOrderLine));
        clientOrder.setOrderLines(Collections.singleton(orderLine));
        publisherOrder.setTitle("publisherorder");
        publisherOrder.setOrderLines(Collections.singleton(publisherOrderLine));

        publisher.setUserName("Publisher");
        publisher.setPassword("esfegr8433");
        publisher.setUserType(UserType.PUBLISHER);

        client.setUserName("Client");
        client.setUserType(UserType.CLIENT);
        //client.setPublisher(publisher);

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


}
