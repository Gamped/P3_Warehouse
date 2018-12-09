package dk.aau.cs.ds303e18.p3warehouse.systemTest;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.models.orders.OrderLine;
import dk.aau.cs.ds303e18.p3warehouse.models.users.*;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import dk.aau.cs.ds303e18.p3warehouse.repositories.*;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Date;

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

    @Autowired
    EmployeeRepository employeeRepository;

    @Before
    public void start() {
        orderRepository.deleteAll();
        publisherRepository.deleteAll();
        productRepository.deleteAll();
        clientRepository.deleteAll();
        employeeRepository.deleteAll();
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

    @Test
    public void addEmployeeToDb() {
        ObjectId employeeId = new ObjectId();
        ObjectId secondEmployeeId = new ObjectId();
        Employee employee = new Employee(employeeId);
        Employee secondEmployee = new Employee(secondEmployeeId);

        employee.setUserType(UserType.EMPLOYEE);
        employee.setUserName("Employee");
        employee.setNickname("Mads");
        employee.setPassword("fsef65686");

        secondEmployee.setUserType(UserType.EMPLOYEE);
        secondEmployee.setUserName("SecondEmployee");
        secondEmployee.setNickname("Hans");
        secondEmployee.setPassword("fsef5446f");

        employeeRepository.save(secondEmployee);
        employeeRepository.save(employee);
    }

    @Test
    public void publisherClient() {
        ObjectId publisherId = new ObjectId();
        ObjectId clientId = new ObjectId();
        ObjectId secondClientId = new ObjectId();
        ObjectId productId = new ObjectId();
        ObjectId secondProductId = new ObjectId();
        ObjectId thirdProductId = new ObjectId();

        Product product = new Product(productId);
        Product secondProduct = new Product(secondProductId);
        Product thirdProduct = new Product(thirdProductId);

        product.setProductId("fe56863");
        product.setQuantity(4);
        product.setProductName("Cycling news");

        secondProduct.setProductId("ef5584613");
        secondProduct.setQuantity(10);
        secondProduct.setProductName("Running news");

        thirdProduct.setProductId("fed2654949");
        thirdProduct.setQuantity(12);
        thirdProduct.setProductName("Car magazine");

        Client client = new Client(clientId);
        Client secondClient = new Client(secondClientId);
        Publisher publisher = new Publisher(publisherId);

        ContactInformation clientContactInformation = new ContactInformation();
        ContactInformation secondClientInformation = new ContactInformation();
        ContactInformation publisherContactInformation = new ContactInformation();

        clientContactInformation.setNickName("Karen");
        clientContactInformation.setEmail("client@kare.rr");
        clientContactInformation.setAddress("fffavej 2");
        clientContactInformation.setPhoneNumber("298522654");
        clientContactInformation.setZipCode("9825");

        secondClientInformation.setNickName("ole");
        secondClientInformation.setEmail("secondClient@fff.rr");
        secondClientInformation.setAddress("govej 2");
        secondClientInformation.setPhoneNumber("2659532659");
        secondClientInformation.setZipCode("6556");

        publisherContactInformation.setNickName("Gose");
        publisherContactInformation.setEmail("publisher@fef.rr");
        publisherContactInformation.setAddress("revej 4");
        publisherContactInformation.setPhoneNumber("1568433546");
        publisherContactInformation.setZipCode("5979");

        client.setUserType(UserType.CLIENT);
        client.setPassword("fegttrh15186648");
        client.setUserName("Client");
        client.setContactInformation(clientContactInformation);

        secondClient.setUserType(UserType.CLIENT);
        secondClient.setPassword("fereg646865");
        secondClient.setUserName("SecondClient");
        secondClient.setContactInformation(secondClientInformation);

        publisher.setUserType(UserType.PUBLISHER);
        publisher.setPassword("fersfe64");
        publisher.setUserName("Publisher");
        publisher.setContactInformation(publisherContactInformation);

        client.addProduct(product);
        client.addProduct(secondProduct);
        secondClient.addProduct(thirdProduct);
        publisher.addClient(client);
        publisher.addClient(secondClient);

        productRepository.save(product);
        productRepository.save(secondProduct);
        productRepository.save(thirdProduct);
        clientRepository.save(client);
        clientRepository.save(secondClient);
        publisherRepository.save(publisher);
    }

    @Test
    public void publisherMoreClients() {
        Publisher publisher = new Publisher(newObjectId());

        Client client = new Client(newObjectId());
        Client secondClient = new Client(newObjectId());
        Client thirdClient = new Client(newObjectId());
        Client fourthClient = new Client(newObjectId());

        Product product = new Product(newObjectId());
        Product secondProduct = new Product(newObjectId());
        Product thirdProduct = new Product(newObjectId());
        Product fourthProduct = new Product(newObjectId());
        Product fifthProduct = new Product(newObjectId());

        product.setProductName("Cycling news");
        product.setQuantity(50);
        product.setProductId("feef888");

        secondProduct.setProductName("slyer");
        secondProduct.setQuantity(10);
        secondProduct.setProductId("f3f455");

        thirdProduct.setProductName("music");
        thirdProduct.setQuantity(20);
        thirdProduct.setProductId("232444");

        fourthProduct.setProductName("movie");
        fourthProduct.setQuantity(61);

    }

    private ObjectId newObjectId() {
        ObjectId id = new ObjectId();

        return id;
    }
}
