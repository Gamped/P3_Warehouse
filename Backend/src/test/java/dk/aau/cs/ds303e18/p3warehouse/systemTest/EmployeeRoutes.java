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

import java.util.ArrayList;
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

    //One publisher with one client
    @Test
    public void checkDatabase() {
        Publisher publisher = new Publisher(newObjectId());
        Client client = new Client(newObjectId());
        Product product = new Product(newObjectId());
        Product clientProduct = new Product(newObjectId());
        Product publisherProduct = new Product(newObjectId());

        product.setProductName("Cycling news");
        product.setQuantity(10);
        product.setProductId("62995962");

        clientProduct.setProductName("Running news");
        clientProduct.setQuantity(20);
        clientProduct.setProductId("sefe5684646");

        publisherProduct.setProductName("Car magazine");
        publisherProduct.setQuantity(35);
        publisherProduct.setProductId("561313");

        Order clientOrder = new Order(newObjectId());
        clientOrder.setTitle("clientorder");
        clientOrder.setOrderId("322434");
        clientOrder.setAddress("mouveh 23");
        clientOrder.setDate(new Date());

        Order publisherOrder = new Order(newObjectId());
        publisherOrder.setTitle("publisherorder");
        publisherOrder.setOrderId("32423525");
        publisherOrder.setAddress("musvej 34");
        publisherOrder.setDate(new Date());

        OrderLine clientOrderLine = new OrderLine(product, 4);
        OrderLine orderLine = new OrderLine(clientProduct, 10);
        OrderLine publisherOrderLine = new OrderLine(publisherProduct, 5);
        ArrayList<OrderLine> orderLines = new ArrayList<>();
        orderLines.add(clientOrderLine);
        orderLines.add(orderLine);

        clientOrder.setTitle("clientorder");
        clientOrder.setOrderLines(orderLines);
        publisherOrder.setTitle("publisherorder");
        publisherOrder.setOrderLines(Collections.singleton(publisherOrderLine));

        publisher.setUserName("Publisher");
        publisher.setPassword("esfegr8433");
        publisher.setUserType(UserType.PUBLISHER);
        publisher.setContactInformation(publisherContactInformation());

        client.setUserName("Client");
        client.setUserType(UserType.CLIENT);
        client.setPassword("fesfrdg5846");
        client.setContactInformation(clientContactInformation());

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
        fourthProduct.setProductId("3435ggg");

        fifthProduct.setProductName("glasses");
        fifthProduct.setQuantity(5);
        fifthProduct.setProductId("fef333344");

        Order order = new Order(newObjectId());
        Order secondOrder = new Order(newObjectId());
        Order thirdOrder = new Order(newObjectId());
        Order fourthOrder = new Order(newObjectId());
        Order fifthOrder = new Order(newObjectId());

        OrderLine orderLine = new OrderLine(product, 20);
        OrderLine secondOrderLine = new OrderLine(secondProduct, 5);
        OrderLine thirdOrderLine = new OrderLine(thirdProduct, 8);
        OrderLine fourthOrderLine = new OrderLine(fourthProduct, 6);
        OrderLine fifthOrderLine = new OrderLine(fifthProduct, 1);

        order.setOrderLines(Collections.singleton(orderLine));
        secondOrder.setOrderLines(Collections.singleton(secondOrderLine));
        thirdOrder.setOrderLines(Collections.singleton(thirdOrderLine));
        fourthOrder.setOrderLines(Collections.singleton(fourthOrderLine));
        fifthOrder.setOrderLines(Collections.singleton(fifthOrderLine));

        order.setTitle("test");
        order.setOrderId("546ffe");
        order.setAddress("musvej 2");
        order.setDate(new Date());

        secondOrder.setTitle("fight");
        secondOrder.setOrderId("556898ddd");
        secondOrder.setAddress("vevej 2");
        secondOrder.setDate(new Date());

        thirdOrder.setTitle("move");
        thirdOrder.setOrderId("4ffe35");
        thirdOrder.setAddress("hudsvej 7");
        thirdOrder.setDate(new Date());

        fourthOrder.setTitle("Run");
        fourthOrder.setOrderId("334553");
        fourthOrder.setAddress("aalborgvej 50");
        fourthOrder.setDate(new Date());

        fifthOrder.setTitle("Bike");
        fifthOrder.setOrderId("34fd");
        fifthOrder.setAddress("houvej 333");
        fifthOrder.setDate(new Date());

        ContactInformation clientContactInformation = new ContactInformation();
        ContactInformation secondClientContactInformation = new ContactInformation();
        ContactInformation thirdClientContactInformation = new ContactInformation();
        ContactInformation fourthClientContactInformation = new ContactInformation();
        ContactInformation publisherContactInformation = new ContactInformation();

        clientContactInformation.setNickName("Mads");
        clientContactInformation.setEmail("client@cc.ff");
        clientContactInformation.setAddress("hovvej 5");
        clientContactInformation.setPhoneNumber("66533269");
        clientContactInformation.setZipCode("5684");

        secondClientContactInformation.setNickName("karin");
        secondClientContactInformation.setEmail("secondclient@cc.ff");
        secondClientContactInformation.setAddress("ryvej 5");
        secondClientContactInformation.setPhoneNumber("26589542");
        secondClientContactInformation.setZipCode("6599");

        thirdClientContactInformation.setNickName("Hans");
        thirdClientContactInformation.setEmail("thirdclient@cc.ff");
        thirdClientContactInformation.setAddress("fogvej 6");
        thirdClientContactInformation.setPhoneNumber("62235421");
        thirdClientContactInformation.setZipCode("6549");

        fourthClientContactInformation.setNickName("kåre");
        fourthClientContactInformation.setEmail("fourthclient@cc.ff");
        fourthClientContactInformation.setAddress("gmvej 7");
        fourthClientContactInformation.setPhoneNumber("65428597");
        fourthClientContactInformation.setZipCode("1236");

        publisherContactInformation.setNickName("Monste");
        publisherContactInformation.setEmail("publisher@cc.ff");
        publisherContactInformation.setAddress("luevej 4");
        publisherContactInformation.setPhoneNumber("29575236");
        publisherContactInformation.setZipCode("1694");

        client.setContactInformation(clientContactInformation);
        client.setUserName("client");
        client.setPassword("fegrdaaa");
        client.setUserType(UserType.CLIENT);
        client.addOrder(secondOrder);
        client.addOrder(thirdOrder);

        secondClient.setContactInformation(secondClientContactInformation);
        secondClient.setUserName("secondclient");
        secondClient.setPassword("fefr8");
        secondClient.setUserType(UserType.CLIENT);
        secondClient.addProduct(fifthProduct);
        secondClient.addOrder(fifthOrder);

        thirdClient.setContactInformation(thirdClientContactInformation);
        thirdClient.setUserName("thirdclient");
        thirdClient.setPassword("fesfew898");
        thirdClient.setUserType(UserType.CLIENT);
        thirdClient.addOrder(fourthOrder);

        fourthClient.setContactInformation(fourthClientContactInformation);
        fourthClient.setUserName("fourthclient");
        fourthClient.setPassword("fesf86rg4r6d");
        fourthClient.setUserType(UserType.CLIENT);
        fourthClient.addProduct(thirdProduct);
        fourthClient.addProduct(fourthProduct);

        publisher.setContactInformation(publisherContactInformation);
        publisher.setUserName("publisher");
        publisher.setPassword("fergerg2664");
        publisher.setUserType(UserType.PUBLISHER);
        publisher.addClient(client);
        publisher.addClient(secondClient);
        publisher.addClient(thirdClient);
        publisher.addClient(fourthClient);
        publisher.addOrder(order);
        publisher.addProduct(product);
        publisher.addProduct(secondProduct);

        productRepository.save(product);
        productRepository.save(secondProduct);
        productRepository.save(thirdProduct);
        productRepository.save(fourthProduct);
        productRepository.save(fifthProduct);
        orderRepository.save(order);
        orderRepository.save(secondOrder);
        orderRepository.save(thirdOrder);
        orderRepository.save(fourthOrder);
        orderRepository.save(fifthOrder);
        clientRepository.save(client);
        clientRepository.save(secondClient);
        clientRepository.save(thirdClient);
        clientRepository.save(fourthClient);
        publisherRepository.save(publisher);

    }

    @Test
    public void publisherNoClientProducts () {
        Publisher publisher = new Publisher(newObjectId());
        Client client = new Client(newObjectId());
        Client secondClient = new Client(newObjectId());
        Product product = new Product(newObjectId());
        Product secondProduct = new Product(newObjectId());
        Order order = new Order(newObjectId());

        product.setProductId("343435d");
        product.setQuantity(52);
        product.setProductName("Running");

        secondProduct.setProductId("3422bdd");
        secondProduct.setQuantity(23);
        secondProduct.setProductName("biking");

        OrderLine orderLine = new OrderLine(product, 5);
        OrderLine secondOrderLine = new OrderLine(secondProduct, 10);

        ArrayList<OrderLine> orderLines = new ArrayList<>();
        orderLines.add(orderLine);
        orderLines.add(secondOrderLine);

        order.setOrderLines(orderLines);
        order.setDate(new Date());
        order.setAddress("govvej 10");
        order.setOrderId("3436346grd");
        order.setTitle("Moving Titles");

        client.setContactInformation(clientContactInformation());
        client.setUserType(UserType.CLIENT);
        client.setUserName("client");
        client.setPassword("fefe516");

        secondClient.setContactInformation(otherContactInformation());
        secondClient.setUserType(UserType.CLIENT);
        secondClient.setUserName("secondclient");
        secondClient.setPassword("ffgrgr33");

        publisher.setContactInformation(publisherContactInformation());
        publisher.setUserType(UserType.PUBLISHER);
        publisher.setUserName("publisher");
        publisher.setPassword("fseffrdgrd");
        publisher.addProduct(product);
        publisher.addProduct(secondProduct);
        publisher.addOrder(order);
        publisher.addClient(client);
        publisher.addClient(secondClient);

        productRepository.save(product);
        productRepository.save(secondProduct);
        orderRepository.save(order);
        clientRepository.save(client);
        clientRepository.save(secondClient);
        publisherRepository.save(publisher);
    }

    @Test
    public void morePublishers() {
        Publisher publisher = new Publisher(newObjectId());
        Publisher secondPublisher = new Publisher(newObjectId());
        Publisher thirdPublisher = new Publisher(newObjectId());

        Client client = new Client(newObjectId());
        Client secondClient = new Client(newObjectId());
        Client thirdClient = new Client(newObjectId());

        Product product = new Product(newObjectId());
        Product secondProduct = new Product(newObjectId());
        Product thirdProduct = new Product(newObjectId());
        Product fourthProduct = new Product(newObjectId());
        Product fifthProduct = new Product(newObjectId());
        Product sixthProduct = new Product(newObjectId());
        Product seventhProduct = new Product(newObjectId());
        Product eightProduct = new Product(newObjectId());

        Order order = new Order(newObjectId());
        Order secondOrder = new Order(newObjectId());
        Order thirdOrder = new Order(newObjectId());
        Order fourthOrder = new Order(newObjectId());
        Order fifthOrder = new Order(newObjectId());
        Order sixthOrder = new Order(newObjectId());

        ContactInformation secondPublisherContactInformation = new ContactInformation();
        ContactInformation thirdPublisherContactInformation = new ContactInformation();

        thirdPublisherContactInformation.setNickName("music store");
        thirdPublisherContactInformation.setEmail("thirdPublisher@ff.cc");
        thirdPublisherContactInformation.setPhoneNumber("87525632");
        thirdPublisherContactInformation.setAddress("gyldenvej 4");
        thirdPublisherContactInformation.setZipCode("2796");

        secondPublisherContactInformation.setNickName("lej og byg");
        secondPublisherContactInformation.setEmail("secondPublisher@ff.cc");
        secondPublisherContactInformation.setPhoneNumber("26546235");
        secondPublisherContactInformation.setAddress("hale 34");
        secondPublisherContactInformation.setZipCode("2695");

        product.setProductName("move");
        product.setQuantity(20);
        product.setProductId("343253beb");

        secondProduct.setProductName("run");
        secondProduct.setQuantity(20);
        secondProduct.setProductId("42432b");

        thirdProduct.setProductName("jump");
        thirdProduct.setQuantity(30);
        thirdProduct.setProductId("24325de");

        fourthProduct.setProductName("bike");
        fourthProduct.setQuantity(60);
        fourthProduct.setProductId("353645765756");

        fifthProduct.setProductName("car");
        fifthProduct.setQuantity(30);
        fifthProduct.setProductId("43256456457");

        sixthProduct.setProductName("ship");
        sixthProduct.setQuantity(65);
        sixthProduct.setProductId("3243354654");

        seventhProduct.setProductName("bus");
        seventhProduct.setQuantity(66);
        seventhProduct.setProductId("3r23543645765");

        eightProduct.setProductName("music");
        eightProduct.setQuantity(26);
        eightProduct.setProductId("35264564765765");

        OrderLine orderLine = new OrderLine(product, 5);
        OrderLine secondOrderLine = new OrderLine(secondProduct, 6);
        OrderLine thirdOrderLine = new OrderLine(thirdProduct, 10);
        OrderLine fourthOrderLine = new OrderLine(fourthProduct, 46);
        OrderLine fifthOrderLine = new OrderLine(fifthProduct, 1);
        OrderLine sixthOrderLine = new OrderLine(sixthProduct, 6);
        OrderLine seventhOrderLine = new OrderLine(seventhProduct, 20);
        OrderLine eightOrderLine = new OrderLine(eightProduct, 10);

        ArrayList<OrderLine> orderLines = new ArrayList<>();
        orderLines.add(orderLine);
        orderLines.add(secondOrderLine);
        orderLines.add(thirdOrderLine);

        order.setOrderLines(orderLines);
        order.setTitle("order");
        order.setDate(new Date());
        order.setAddress("mour 4");
        order.setOrderId("35223645654ddd");

        secondOrder.setOrderLines(Collections.singleton(fifthOrderLine));
        secondOrder.setTitle("secondorder");
        secondOrder.setDate(new Date());
        secondOrder.setAddress("foul 23");
        secondOrder.setOrderId("242543643678");

        thirdOrder.setOrderLines(Collections.singleton(fourthOrderLine));
        thirdOrder.setTitle("thirdorder");
        thirdOrder.setDate(new Date());
        thirdOrder.setAddress("doo 4");
        thirdOrder.setOrderId("325346436");

        fourthOrder.setOrderLines(Collections.singleton(sixthOrderLine));
        fourthOrder.setTitle("fourthorder");
        fourthOrder.setDate(new Date());
        fourthOrder.setAddress("yellow 2");
        fourthOrder.setOrderId("3254368888");

        fifthOrder.setOrderLines(Collections.singleton(seventhOrderLine));
        fifthOrder.setTitle("fifthorder");
        fifthOrder.setDate(new Date());
        fifthOrder.setAddress("moon 90");
        fifthOrder.setOrderId("325378897430");

        sixthOrder.setOrderLines(Collections.singleton(eightOrderLine));
        sixthOrder.setTitle("sixthorder");
        sixthOrder.setDate(new Date());
        sixthOrder.setAddress("sun 33");
        sixthOrder.setOrderId("3536347568");

        client.setContactInformation(clientContactInformation());
        client.setUserType(UserType.CLIENT);
        client.setUserName("client");
        client.setPassword("24fsefsefrg");
        client.addOrder(secondOrder);
        client.addProduct(fifthProduct);

        secondClient.setContactInformation(otherContactInformation());
        secondClient.setUserType(UserType.CLIENT);
        secondClient.setUserName("secondclient");
        secondClient.setPassword("esfesgrs");
        secondClient.addOrder(order);
        secondClient.addProduct(product);
        secondClient.addProduct(secondProduct);
        secondClient.addProduct(thirdProduct);

        thirdClient.setContactInformation(extraContactInformation());
        thirdClient.setUserType(UserType.CLIENT);
        thirdClient.setUserName("thirdclient");
        thirdClient.setPassword("fesgr4546");
        thirdClient.addOrder(fourthOrder);
        thirdClient.addProduct(sixthProduct);

        publisher.setContactInformation(publisherContactInformation());
        publisher.setUserType(UserType.PUBLISHER);
        publisher.setUserName("publisher");
        publisher.setPassword("esfesfreg3");
        publisher.addClient(client);
        publisher.addClient(secondClient);
        publisher.addOrder(thirdOrder);
        publisher.addProduct(fourthProduct);

        secondPublisher.setContactInformation(secondPublisherContactInformation);
        secondPublisher.setUserType(UserType.PUBLISHER);
        secondPublisher.setUserName("secondpublisher");
        secondPublisher.setPassword("erw3ret544");
        secondPublisher.addClient(thirdClient);
        secondPublisher.addOrder(fifthOrder);
        secondPublisher.addProduct(seventhProduct);

        thirdPublisher.setContactInformation(thirdPublisherContactInformation);
        thirdPublisher.setUserType(UserType.PUBLISHER);
        thirdPublisher.setUserName("thirdpublisher");
        thirdPublisher.setPassword("r43tdhytf");
        thirdPublisher.addOrder(sixthOrder);
        thirdPublisher.addProduct(eightProduct);

        productRepository.save(product);
        productRepository.save(secondProduct);
        productRepository.save(thirdProduct);
        productRepository.save(fourthProduct);
        productRepository.save(fifthProduct);
        productRepository.save(sixthProduct);
        productRepository.save(seventhProduct);
        productRepository.save(eightProduct);

        orderRepository.save(order);
        orderRepository.save(secondOrder);
        orderRepository.save(thirdOrder);
        orderRepository.save(fourthOrder);
        orderRepository.save(fifthOrder);
        orderRepository.save(sixthOrder);

        clientRepository.save(client);
        clientRepository.save(secondClient);
        clientRepository.save(thirdClient);

        publisherRepository.save(publisher);
        publisherRepository.save(secondPublisher);
        publisherRepository.save(thirdPublisher);
    }

    private ObjectId newObjectId() {
        ObjectId id = new ObjectId();

        return id;
    }

    private ContactInformation clientContactInformation() {
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setNickName("karin");
        contactInformation.setEmail("secondclient@cc.ff");
        contactInformation.setAddress("ryvej 5");
        contactInformation.setPhoneNumber("26589542");
        contactInformation.setZipCode("6599");

        return contactInformation;
    }

    private ContactInformation publisherContactInformation() {
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setNickName("Monste");
        contactInformation.setEmail("publisher@cc.ff");
        contactInformation.setAddress("luevej 4");
        contactInformation.setPhoneNumber("29575236");
        contactInformation.setZipCode("1694");

        return contactInformation;
    }

    private ContactInformation otherContactInformation() {
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setNickName("Karen");
        contactInformation.setEmail("client@kare.rr");
        contactInformation.setAddress("fffavej 2");
        contactInformation.setPhoneNumber("298522654");
        contactInformation.setZipCode("9825");

        return contactInformation;
    }

    private ContactInformation extraContactInformation() {
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setNickName("Gose");
        contactInformation.setEmail("publisher@fef.rr");
        contactInformation.setAddress("revej 4");
        contactInformation.setPhoneNumber("1568433546");
        contactInformation.setZipCode("5979");
        return contactInformation;
    }
}
