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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockClientData.*;
import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockProductData.*;
import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockPublisherData.*;
import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockOrderData.*;
import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockEmployeeData.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class MockDataCreator {

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

        Employee employee = makeEmployee();
        Employee secondEmployee = makeSecondEmployee();
        Employee thirdEmployee = makeThirdEmployee();
        Employee fourthEmployee = makeFourthEmployee();
        Employee fifthEmployee = makeFifthEmployee();

        User user = new User(thirdEmployee.getId());
        User user2 = new User(fourthEmployee.getId());
        User user3 = new User(fifthEmployee.getId());
        User user4 = new User(employee.getId());
        User user5 = new User(secondEmployee.getId());
        BeanUtils.copyProperties(employee, user4);
        BeanUtils.copyProperties(secondEmployee, user5);
        BeanUtils.copyProperties(thirdEmployee, user);
        BeanUtils.copyProperties(fourthEmployee, user2);
        BeanUtils.copyProperties(fifthEmployee, user3);

        employeeRepository.save(employee);
        employeeRepository.save(secondEmployee);
        employeeRepository.save(thirdEmployee);
        employeeRepository.save(fourthEmployee);
        employeeRepository.save(fifthEmployee);

        userRepository.save(user);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);
    }

    @Test
    public void makeData() {

        orderRepository.deleteAll();
        publisherRepository.deleteAll();
        productRepository.deleteAll();
        clientRepository.deleteAll();
        employeeRepository.deleteAll();
        userRepository.deleteAll();
        independentClient();
        makeEmployees();

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

        ArrayList<OrderLine> a = new ArrayList<>();
        ArrayList<OrderLine> b = new ArrayList<>();
        ArrayList<OrderLine> c = new ArrayList<>();
        ArrayList<OrderLine> d = new ArrayList<>();
        ArrayList<OrderLine> e = new ArrayList<>();

        a.add(fourthOrderLine);
        b.add(fifthOrderLine);
        c.add(sixthOrderLine);
        d.add(seventhOrderLine);
        e.add(eightOrderLine);

        order.setOrderLines(orderLines);
        secondOrder.setOrderLines(a);
        thirdOrder.setOrderLines(b);
        fourthOrder.setOrderLines(c);
        fifthOrder.setOrderLines(d);
        sixthOrder.setOrderLines(e);

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
        orderLines.add(publisherOrderLine);
        publisherOrder.setOrderLines(orderLines);

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

        ArrayList<OrderLine> a = new ArrayList<>();
        ArrayList<OrderLine> b = new ArrayList<>();
        ArrayList<OrderLine> c = new ArrayList<>();
        ArrayList<OrderLine> d = new ArrayList<>();
        ArrayList<OrderLine> e = new ArrayList<>();

        a.add(orderLine);
        b.add(secondOrderLine);
        c.add(thirdOrderLine);
        d.add(fourthOrderLine);
        e.add(fifthOrderLine);

        order.setOrderLines(a);
        secondOrder.setOrderLines(b);
        thirdOrder.setOrderLines(c);
        fourthOrder.setOrderLines(d);
        fifthOrder.setOrderLines(e);

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
        ArrayList<OrderLine> a = new ArrayList<>();
        a.add(orderLine);
        order.setOrderLines(a);

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
}
