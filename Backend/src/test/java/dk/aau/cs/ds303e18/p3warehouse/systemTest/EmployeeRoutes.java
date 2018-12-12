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
import org.springframework.beans.BeanUtils;
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

    @Autowired
    UserRepository userRepository;

    @Before
    public void start() {
        orderRepository.deleteAll();
        publisherRepository.deleteAll();
        productRepository.deleteAll();
        clientRepository.deleteAll();
        employeeRepository.deleteAll();
        userRepository.deleteAll();
    }

    public void makeEmployees() {
        Employee emp = new Employee(new ObjectId());
        emp.setNickname("Jane");
        emp.setUserType(UserType.EMPLOYEE);
        emp.setPassword("123");
        emp.setUserName("jane");

        Employee emp2 = new Employee(new ObjectId());
        emp2.setNickname("Casper");
        emp2.setUserType(UserType.EMPLOYEE);
        emp2.setPassword("123");
        emp2.setUserName("casper");

        Employee emp3 = new Employee(new ObjectId());
        emp3.setNickname("Steen");
        emp3.setUserType(UserType.EMPLOYEE);
        emp3.setPassword("123");
        emp3.setUserName("steen");

        employeeRepository.save(emp);
        employeeRepository.save(emp2);
        employeeRepository.save(emp3);



        User user = new User(emp.getId());
        User user2 = new User(emp2.getId());
        User user3 = new User(emp3.getId());
        BeanUtils.copyProperties(emp, user);
        BeanUtils.copyProperties(emp2, user2);
        BeanUtils.copyProperties(emp3, user3);


        userRepository.save(user);
        userRepository.save(user2);
        userRepository.save(user3);

    }

    @Test
    public void makeData() {
        independentClient();
        addEmployeeToDb();

        Product product = makeProduct();
        Product secondProduct = makeSecondProduct();
        Product thirdProduct = makeThirdProduct();
        Product fourthProduct = makeFourthProduct();
        Product fifthProduct = makeFifthProduct();
        Product sixthProduct = makeSixthProduct();
        Product seventhProduct = makeSeventhProduct();
        Product eightProduct = makeEigthProduct();

        Order order = makeOrder();
        Order secondOrder = makeSecondOrder();
        Order thirdOrder = makeThirdOrder();
        Order fourthOrder = makeFourthOrder();
        Order fifthOrder = makeFifthOrder();
        Order sixthOrder = makeSixthOrder();

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
        secondOrder.setOrderLines(Collections.singleton(fifthOrderLine));
        thirdOrder.setOrderLines(Collections.singleton(fourthOrderLine));
        fourthOrder.setOrderLines(Collections.singleton(sixthOrderLine));
        fifthOrder.setOrderLines(Collections.singleton(seventhOrderLine));
        sixthOrder.setOrderLines(Collections.singleton(eightOrderLine));

        Client client = makeClient();
        client.addOrder(secondOrder);
        secondOrder.setOwner(client);
        client.addProduct(fifthProduct);
        fifthProduct.setOwner(client);

        Client secondClient = makeSecondClient();
        secondClient.addOrder(order);
        order.setOwner(secondClient);
        secondClient.addProduct(product);
        product.setOwner(secondClient);
        secondClient.addProduct(secondProduct);
        secondProduct.setOwner(secondClient);
        secondClient.addProduct(thirdProduct);
        thirdProduct.setOwner(secondClient);

        Client thirdClient = makeThirdClient();
        thirdClient.addOrder(fourthOrder);
        fourthOrder.setOwner(thirdClient);
        thirdClient.addProduct(sixthProduct);
        sixthProduct.setOwner(thirdClient);

        Publisher publisher = makePublisher();
        publisher.addClient(client);
        client.setPublisher(publisher);
        publisher.addClient(secondClient);
        secondClient.setPublisher(publisher);
        publisher.addOrder(thirdOrder);
        thirdOrder.setOwner(publisher);
        publisher.addProduct(fourthProduct);
        fourthProduct.setOwner(publisher);


        Publisher secondPublisher = makeSecondPublisher();
        secondPublisher.addClient(thirdClient);
        thirdClient.setPublisher(secondPublisher);
        secondPublisher.addOrder(fifthOrder);
        fifthOrder.setOwner(secondPublisher);
        secondPublisher.addProduct(seventhProduct);
        seventhProduct.setOwner(secondPublisher);


        Publisher thirdPublisher = makeThirdPublisher();
        thirdPublisher.addOrder(sixthOrder);
        sixthOrder.setOwner(thirdPublisher);
        thirdPublisher.addProduct(eightProduct);
        eightProduct.setOwner(thirdPublisher);

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

        System.out.println(order.getCity() + "\n" + order.getPhoneNumber() + "\n" + order.getCountry() + "\n"
        + order.getContactPerson() + "\n" + order.getCompany() + "\n" + order.getZipCode());
        clientRepository.save(client);
        clientRepository.save(secondClient);
        clientRepository.save(thirdClient);

        publisherRepository.save(publisher);
        publisherRepository.save(secondPublisher);
        publisherRepository.save(thirdPublisher);

        User user = new User(publisher.getId());
        User user2 = new User(secondPublisher.getId());
        User user3 = new User(thirdPublisher.getId());
        User user4 = new User(client.getId());
        User user5 = new User(secondClient.getId());
        User user6 = new User(thirdClient.getId());
        BeanUtils.copyProperties(publisher, user);
        BeanUtils.copyProperties(secondPublisher, user2);
        BeanUtils.copyProperties(thirdPublisher, user3);
        BeanUtils.copyProperties(client, user4);
        BeanUtils.copyProperties(secondClient, user5);
        BeanUtils.copyProperties(thirdClient, user6);

        userRepository.save(user);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);
        userRepository.save(user6);
    }

    //One publisher with one client
    @Test
    public void onePublisherAndOneClient() {
        Product product = makeProduct();
        Product clientProduct = makeEigthProduct();
        Product publisherProduct = makeFifthProduct();

        Order clientOrder = makeOrder();
        Order publisherOrder = makeFifthOrder();


        OrderLine clientOrderLine = new OrderLine(product, 4);
        OrderLine orderLine = new OrderLine(clientProduct, 10);
        OrderLine publisherOrderLine = new OrderLine(publisherProduct, 5);
        ArrayList<OrderLine> orderLines = new ArrayList<>();
        orderLines.add(clientOrderLine);
        orderLines.add(orderLine);

        clientOrder.setOrderLines(orderLines);
        publisherOrder.setOrderLines(Collections.singleton(publisherOrderLine));

        Publisher publisher = makePublisher();
        Client client = makeClient();

        client.addProduct(product);
        product.setOwner(client);
        client.addProduct(clientProduct);
        clientProduct.setOwner(client);
        client.addOrder(clientOrder);
        clientOrder.setOwner(client);

        publisher.addProduct(publisherProduct);
        publisherProduct.setOwner(publisher);
        publisher.addOrder(publisherOrder);
        publisherOrder.setOwner(publisher);
        publisher.addClient(client);
        client.setPublisher(publisher);

        productRepository.save(product);
        productRepository.save(clientProduct);
        productRepository.save(publisherProduct);
        orderRepository.save(clientOrder);
        orderRepository.save(publisherOrder);
        clientRepository.save(client);
        publisherRepository.save(publisher);

        User user = new User(client.getId());
        User user2 = new User(publisher.getId());
        BeanUtils.copyProperties(client, user);
        BeanUtils.copyProperties(publisher, user2);

        userRepository.save(user);
        userRepository.save(user2);
    }

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

        User user = new User(employee.getId());
        User user2 = new User(secondEmployee.getId());
        BeanUtils.copyProperties(employee, user);
        BeanUtils.copyProperties(secondEmployee, user2);

        userRepository.save(user);
        userRepository.save(user2);
    }

    @Test
    public void publisherClient() {
        Client client = makeClient();
        Client secondClient = makeSecondClient();
        Product product = makeProduct();
        Product secondProduct = makeSecondProduct();
        Product thirdProduct = makeThirdProduct();
        Publisher publisher = makePublisher();

        client.addProduct(product);
        product.setOwner(client);
        client.addProduct(secondProduct);
        secondProduct.setOwner(client);
        secondClient.addProduct(thirdProduct);
        thirdProduct.setOwner(secondClient);
        publisher.addClient(client);
        client.setPublisher(publisher);
        publisher.addClient(secondClient);
        secondClient.setPublisher(publisher);

        productRepository.save(product);
        productRepository.save(secondProduct);
        productRepository.save(thirdProduct);
        clientRepository.save(client);
        clientRepository.save(secondClient);
        publisherRepository.save(publisher);

        User user = new User(client.getId());
        User user2 = new User(secondClient.getId());
        User user3 = new User(publisher.getId());
        BeanUtils.copyProperties(user, user);
        BeanUtils.copyProperties(user2, user2);
        BeanUtils.copyProperties(user3, user3);

        userRepository.save(user);
        userRepository.save(user2);
        userRepository.save(user3);
    }

    @Test
    public void publisherMoreClients() {
        Product product = makeProduct();
        Product secondProduct = makeSecondProduct();
        Product thirdProduct = makeThirdProduct();
        Product fourthProduct = makeFourthProduct();
        Product fifthProduct = makeFifthProduct();

        Order order = makeOrder();
        Order secondOrder = makeSecondOrder();
        Order thirdOrder = makeThirdOrder();
        Order fourthOrder = makeFourthOrder();
        Order fifthOrder = makeFifthOrder();

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

        Client client = makeExtraClient();
        client.addProduct(secondProduct);
        secondProduct.setOwner(client);
        client.addProduct(thirdProduct);
        thirdProduct.setOwner(client);
        client.addOrder(secondOrder);
        secondOrder.setOwner(client);
        client.addOrder(thirdOrder);
        thirdOrder.setOwner(client);

        Client secondClient = makeSecondClient();
        secondClient.addProduct(fifthProduct);
        fifthProduct.setOwner(secondClient);
        secondClient.addOrder(fifthOrder);
        fifthOrder.setOwner(secondClient);

        Client thirdClient = makeThirdClient();
        thirdClient.addProduct(fourthProduct);
        fourthProduct.setOwner(thirdClient);
        thirdClient.addOrder(fourthOrder);
        fourthOrder.setOwner(thirdClient);

        Client fourthClient = makeClient();

        Publisher publisher = makePublisher();
        publisher.addClient(client);
        client.setPublisher(publisher);
        publisher.addClient(secondClient);
        secondClient.setPublisher(publisher);
        publisher.addClient(thirdClient);
        thirdClient.setPublisher(publisher);
        publisher.addClient(fourthClient);
        fourthClient.setPublisher(publisher);
        publisher.addProduct(product);
        product.setOwner(publisher);
        publisher.addOrder(order);
        order.setOwner(publisher);

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

        User user = new User(client.getId());
        User user2 = new User(secondClient.getId());
        User user3 = new User(thirdClient.getId());
        User user4 = new User(fourthClient.getId());
        User user5 = new User(publisher.getId());
        BeanUtils.copyProperties(client, user);
        BeanUtils.copyProperties(secondClient, user2);
        BeanUtils.copyProperties(thirdClient, user3);
        BeanUtils.copyProperties(fourthClient, user4);
        BeanUtils.copyProperties(publisher, user5);

        userRepository.save(user);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);

    }

    @Test
    public void publisherNoClientProducts () {
        Product product = makeProduct();
        Product secondProduct = makeSecondProduct();
        Order order = makeOrder();

        OrderLine orderLine = new OrderLine(product, 5);
        OrderLine secondOrderLine = new OrderLine(secondProduct, 10);

        ArrayList<OrderLine> orderLines = new ArrayList<>();
        orderLines.add(orderLine);
        orderLines.add(secondOrderLine);

        order.setOrderLines(orderLines);

        Client client = makeClient();
        Client secondClient = makeSecondClient();

        Publisher publisher = makePublisher();
        publisher.addProduct(product);
        product.setOwner(publisher);
        publisher.addProduct(secondProduct);
        secondProduct.setOwner(publisher);
        publisher.addOrder(order);
        order.setOwner(publisher);
        publisher.addClient(client);
        client.setPublisher(publisher);
        publisher.addClient(secondClient);
        secondClient.setPublisher(publisher);

        productRepository.save(product);
        productRepository.save(secondProduct);
        orderRepository.save(order);
        clientRepository.save(client);
        clientRepository.save(secondClient);
        publisherRepository.save(publisher);

        User user = new User(publisher.getId());
        User user2 = new User(client.getId());
        User user3 = new User(secondClient.getId());
        BeanUtils.copyProperties(publisher, user);
        BeanUtils.copyProperties(client, user2);
        BeanUtils.copyProperties(secondClient, user3);

        userRepository.save(user);
        userRepository.save(user2);
        userRepository.save(user3);
    }

    public void independentClient() {
        Order order = makeExtraOrder();
        Product product = makeExtraProduct();

        OrderLine orderLine = new OrderLine(product, 25);
        order.setOrderLines(Collections.singleton(orderLine));

        Client client = makeExtraClient();
        client.addProduct(product);
        product.setOwner(client);
        client.addOrder(order);
        order.setOwner(client);

        productRepository.save(product);
        orderRepository.save(order);
        clientRepository.save(client);

        User user = new User(client.getId());
        BeanUtils.copyProperties(client, user);

        userRepository.save(user);
    }

    private ObjectId newObjectId() {
        ObjectId id = new ObjectId();


        return id;
    }

    private Product makeProduct() {
        Product product = new Product(newObjectId());

        product.setProductName("move");
        product.setQuantity(20);
        product.setProductId("343253beb");


        return product;
    }


    private Product makeSecondProduct() {
        Product product = new Product(newObjectId());

        product.setProductName("run");
        product.setQuantity(20);
        product.setProductId("42432b");

        return product;
    }

    private Product makeThirdProduct() {
        Product product = new Product(newObjectId());

        product.setProductName("jump");
        product.setQuantity(30);
        product.setProductId("24325de");

        return product;
    }

    private Product makeFourthProduct() {
        Product product = new Product(newObjectId());

        product.setProductName("bike");
        product.setQuantity(60);
        product.setProductId("353645765756");

        return product;
    }

    private Product makeFifthProduct() {
        Product product = new Product(newObjectId());

        product.setProductName("car");
        product.setQuantity(30);
        product.setProductId("43256456457");

        return product;
    }

    private Product makeSixthProduct() {
        Product product = new Product(newObjectId());

        product.setProductName("ship");
        product.setQuantity(65);
        product.setProductId("3243354654");

        return product;
    }

    private Product makeSeventhProduct() {
        Product product = new Product(newObjectId());

        product.setProductName("bus");
        product.setQuantity(66);
        product.setProductId("3r23543645765");

        return product;
    }

    private Product makeEigthProduct() {
        Product product = new Product(newObjectId());

        product.setProductName("music");
        product.setQuantity(26);
        product.setProductId("35264564765765");

        return product;
    }

    private Client makeClient() {
        Client client = new Client(newObjectId());
        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setNickName("karin");
        contactInformation.setEmail("secondclient@cc.ff");
        contactInformation.setAddress("ryvej 5");
        contactInformation.setPhoneNumber("26589542");
        contactInformation.setZipCode("6599");
        contactInformation.setCity("Bamse");

        client.setContactInformation(contactInformation);
        client.setUserType(UserType.CLIENT);
        client.setUserName("client");
        client.setPassword("24fsefsefrg");


        return client;
    }

    private Publisher makePublisher() {
        Publisher publisher = new Publisher(newObjectId());
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setNickName("Monste");
        contactInformation.setEmail("publisher@cc.ff");
        contactInformation.setAddress("luevej 4");
        contactInformation.setPhoneNumber("29575236");
        contactInformation.setZipCode("1694");
        contactInformation.setCity("Århus");

        publisher.setContactInformation(contactInformation);
        publisher.setUserType(UserType.PUBLISHER);
        publisher.setUserName("publisher");
        publisher.setPassword("esfesfreg3");

        return publisher;
    }

    private Client makeSecondClient() {
        Client client = new Client(newObjectId());
        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setNickName("Karen");
        contactInformation.setEmail("client@kare.rr");
        contactInformation.setAddress("fffavej 2");
        contactInformation.setPhoneNumber("298522654");
        contactInformation.setZipCode("9825");
        contactInformation.setCity("Hadsten");

        client.setContactInformation(contactInformation);
        client.setUserType(UserType.CLIENT);
        client.setUserName("secondclient");
        client.setPassword("esfesgrs");

        return client;
    }

    private Client makeThirdClient() {
        Client client = new Client(newObjectId());
        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setNickName("Gose");
        contactInformation.setEmail("publisher@fef.rr");
        contactInformation.setAddress("revej 4");
        contactInformation.setPhoneNumber("1568433546");
        contactInformation.setZipCode("5979");
        contactInformation.setCity("Rye");

        client.setUserType(UserType.CLIENT);
        client.setUserName("thirdclient");
        client.setPassword("fesgr4546");
        client.setContactInformation(contactInformation);

        return client;
    }

    private Publisher makeSecondPublisher() {
        Publisher publisher = new Publisher(newObjectId());
        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setNickName("lej og byg");
        contactInformation.setEmail("secondPublisher@ff.cc");
        contactInformation.setPhoneNumber("26546235");
        contactInformation.setAddress("hale 34");
        contactInformation.setZipCode("2695");
        contactInformation.setCity("Hals");

        publisher.setUserType(UserType.PUBLISHER);
        publisher.setUserName("secondpublisher");
        publisher.setPassword("erw3ret544");
        publisher.setContactInformation(contactInformation);

        return publisher;
    }

    private Publisher makeThirdPublisher() {
        Publisher publisher = new Publisher(newObjectId());
        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setNickName("music store");
        contactInformation.setEmail("thirdPublisher@ff.cc");
        contactInformation.setPhoneNumber("87525632");
        contactInformation.setAddress("gyldenvej 4");
        contactInformation.setZipCode("2796");
        contactInformation.setCity("Padborg");

        publisher.setUserType(UserType.PUBLISHER);
        publisher.setUserName("thirdpublisher");
        publisher.setPassword("r43tdhytf");
        publisher.setContactInformation(contactInformation);

        return publisher;
    }

    private Order makeOrder() {
        Order order = new Order(newObjectId());

        order.setTitle("order");
        order.setDate(new Date());
        order.setAddress("mour 4");
        order.setOrderId("35223645654ddd");
        order.setCity("Serene");
        order.setPhoneNumber("66498726");
        order.setZipCode("5979");
        order.setCountry("Denmark");
        order.setCompany("sports shop");
        order.setContactPerson("Molly");

        return order;
    }

    private Order makeSecondOrder() {
        Order order = new Order(newObjectId());

        order.setTitle("secondorder");
        order.setDate(new Date());
        order.setAddress("foul 23");
        order.setOrderId("242543643678");
        order.setCity("Moonlight");
        order.setPhoneNumber("56846987");
        order.setZipCode("9985");
        order.setCountry("Denmark");
        order.setCompany("music store");
        order.setContactPerson("ole");

        return order;
    }


    private Order makeThirdOrder() {
        Order order = new Order(newObjectId());

        order.setTitle("thirdorder");
        order.setDate(new Date());
        order.setAddress("doo 4");
        order.setOrderId("325346436");
        order.setCity("Stone");
        order.setPhoneNumber("43599856");
        order.setZipCode("5955");
        order.setCountry("Denmark");
        order.setCompany("book store");
        order.setContactPerson("Hans");

        return order;
    }

    private Order makeFourthOrder() {
        Order order = new Order(newObjectId());

        order.setTitle("fourthorder");
        order.setDate(new Date());
        order.setAddress("yellow 2");
        order.setOrderId("3254368888");
        order.setCity("Gesser");
        order.setPhoneNumber("46488798");
        order.setZipCode("1354");
        order.setCountry("Denmark");
        order.setCompany("Museum");
        order.setContactPerson("Mogens");

        return order;
    }

    private Order makeFifthOrder() {
        Order order = new Order(newObjectId());

        order.setTitle("fifthorder");
        order.setDate(new Date());
        order.setAddress("moon 90");
        order.setOrderId("325378897430");
        order.setCity("Smalling");
        order.setPhoneNumber("15988526");
        order.setZipCode("9335");
        order.setCountry("Sweeden");
        order.setCompany("car magazine maker");
        order.setContactPerson("Maren");

        return order;
    }

    private Order makeSixthOrder() {
        Order order = new Order(newObjectId());

        order.setTitle("sixthorder");
        order.setDate(new Date());
        order.setAddress("sun 33");
        order.setOrderId("3536347568");
        order.setCity("Kunsten");
        order.setPhoneNumber("26885487");
        order.setZipCode("9526");
        order.setCountry("Denmark");
        order.setCompany("general store");
        order.setContactPerson("Mark");

        return order;
    }

    public Client makeExtraClient() {
        Client client = new Client(newObjectId());
        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setNickName("Hans");
        contactInformation.setEmail("fes@gr.gdr");
        contactInformation.setPhoneNumber("15334888");
        contactInformation.setAddress("møllevej 4");
        contactInformation.setZipCode("5497");
        contactInformation.setCity("Vice country");

        client.setUserName("Client");
        client.setPassword("3wdgr4");
        client.setUserType(UserType.CLIENT);
        client.setContactInformation(contactInformation);

        return client;
    }

    public Product makeExtraProduct() {
        Product product = new Product(newObjectId());

        product.setQuantity(400);
        product.setProductName("cycling news");
        product.setProductId("342525");

        return product;
    }

    public Order makeExtraOrder() {
        Order order = new Order(newObjectId());

        order.setTitle("flyers");
        order.setOrderId("3255");
        order.setAddress("musvej 3");
        order.setDate(new Date());
        order.setCity("Aalborg");
        order.setPhoneNumber("99635485");
        order.setZipCode("9523");
        order.setCountry("Denmark");
        order.setCompany("News company");
        order.setContactPerson("Oliver");

        return order;
    }
}
