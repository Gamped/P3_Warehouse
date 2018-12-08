package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestClientModel;
import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestProductModel;
import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestPublisherModel;
import dk.aau.cs.ds303e18.p3warehouse.models.users.*;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import dk.aau.cs.ds303e18.p3warehouse.repositories.*;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {

    @InjectMocks
    EmployeeController employeeController;

    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    ClientRepository clientRepository;

    @Mock
    PublisherRepository publisherRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    OrderRepository orderRepository;

    @Test
    public void employeeControllerLoads() throws Exception {
        assertThat(employeeController).isNotNull();
    }

    @Before
    public void start() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testEmployeeCreatePublisherProduct() {
        ObjectId customerId = new ObjectId();

        Publisher publisher = new Publisher(customerId);

        RestProductModel restProductModel = new RestProductModel();
        restProductModel.setProductName("Cycling news");
        restProductModel.setQuantity(40);
        restProductModel.setProductId("3246758222");

        publisher.setUserType(UserType.PUBLISHER);
        when(publisherRepository.findById(publisher.getId())).thenReturn(Optional.of(publisher));

        String createdString = employeeController.createProduct(publisher.getHexId(),
                String.valueOf(publisher.getUserType()), restProductModel);

        verify(publisherRepository).findById(publisher.getId());

        assertEquals("Created!", createdString);
    }

    @Test
    public void testEmployeeCreateClientProduct() {
        ObjectId customerId = new ObjectId();
        Client client = new Client(customerId);

        RestProductModel restProductModel = new RestProductModel();
        restProductModel.setProductName("Car magazine");
        restProductModel.setQuantity(200);
        restProductModel.setProductId("32525411414525");
        client.setUserType(UserType.CLIENT);

        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));

        String createdClientProduct = employeeController.createProduct(client.getHexId(),
                String.valueOf(client.getUserType()), restProductModel);

        verify(clientRepository).findById(client.getId());

        assertEquals("Created!", createdClientProduct);
    }

    @Test
    public void employeeCreateProduct() {
        ObjectId id = new ObjectId();
        Publisher publisher = new Publisher(id);
        RestProductModel restProductModel = new RestProductModel();

        String createdString = employeeController.createProduct(publisher.getHexId(),
               "DEFAULT", restProductModel);

        assertEquals("Could not create, customerId or userType is not set!", createdString);

    }

    @Test
    public void testEmployeeCreatePublisher() {
        ObjectId id = new ObjectId();
        Publisher publisher = new Publisher(id);
        publisher.setUserType(UserType.PUBLISHER);
        RestPublisherModel restPublisherModel = new RestPublisherModel();
        restPublisherModel.setUserName("bo");

        String createdPublisher = employeeController.createPublisher(restPublisherModel);

        System.out.println(createdPublisher);
        assertNotNull(createdPublisher);
    }

    @Test
    public void testEmployeeCreateClient() {
        ObjectId id = new ObjectId();
        Client client = new Client(id);
        client.setUserType(UserType.CLIENT);

        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setNickName("karin");
        contactInformation.setEmail("publisher@ff.cc");
        contactInformation.setAddress("julegade 2");
        contactInformation.setPhoneNumber("25789879");
        contactInformation.setZipCode("8954");

        RestClientModel restClientModel = new RestClientModel();
        restClientModel.setUserName("publisher");
        restClientModel.setPassword("21365876wefwefewf");
        restClientModel.setContactInformation(contactInformation);

        String createdClient = employeeController.createClient(restClientModel);

        assertEquals("Created!", createdClient);
    }

    @Test
    public void testEmployeeFindAllProducts() {
        ObjectId productId = new ObjectId();
        ObjectId secondProductId = new ObjectId();
        ObjectId thirdProductId = new ObjectId();

        Product product = new Product(productId);
        Product secondProduct = new Product(secondProductId);
        Product thirdProduct = new Product(thirdProductId);

        product.setProductId("234363464");
        product.setQuantity(265);
        product.setProductName("books about stuff");

        secondProduct.setProductId("2425336536");
        secondProduct.setQuantity(9000);
        secondProduct.setProductName("promotional flyers");

        thirdProduct.setProductId("6475876978970");
        thirdProduct.setQuantity(644);
        thirdProduct.setProductName("notes");

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(secondProduct);
        productList.add(thirdProduct);

        when(productRepository.findAll()).thenReturn(productList);

        Collection<Product> products = employeeController.findAllProducts();

        verify(productRepository).findAll();

        System.out.println(products);
        assertEquals(productList, products);
        assertEquals(3, products.size());
    }

    @Test
    public void testEmployeeFindAllUsers() {
        ObjectId clientId = new ObjectId();
        ObjectId publisherId = new ObjectId();
        ObjectId employeeId = new ObjectId();

        Client client = new Client(clientId);
        Publisher publisher = new Publisher(publisherId);
        Employee employee = new Employee(employeeId);

        List<User> userList = new LinkedList<>();
        userList.add(client);
        userList.add(publisher);
        userList.add(employee);

        when(userRepository.findAll()).thenReturn(userList);

        Collection<User> users = employeeController.findAllUsers();

        verify(userRepository).findAll();

        assertEquals(userList, users);
        assertEquals(3, users.size());
    }

    @Test
    public void testFindEmployeeById() {
        ObjectId id = new ObjectId();
        Employee employee = new Employee(id);

        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        Employee retrievedEmployee = employeeController.getOneEmployee(String.valueOf(id));
        verify(employeeRepository).findById(id);
        assertEquals(employee.getId(), retrievedEmployee.getId());
    }

    @Test
    public void testEmployeeFindProductById() {
        ObjectId id = new ObjectId();
        Product product = new Product(id);
        product.setQuantity(25);

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        Optional<Product> optProduct = employeeController.findProductById(product.getHexId());
        Product retrievedProduct = optProduct.get();

        verify(productRepository).findById(product.getId());

        assertEquals(product.getHexId(), retrievedProduct.getHexId());
        assertEquals(product.getQuantity(), retrievedProduct.getQuantity());
    }

    @Test
    public void testUpdateEmployee() {
        ObjectId id = new ObjectId();
        Employee employee = new Employee(id);

        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        String updatedEmployee = employeeController.updateEmployee(String.valueOf(employee.getId()),
                "børge");

        verify(employeeRepository).findById(employee.getId());

        assertNotNull(employee.getNickname());
        assertTrue(updatedEmployee.contains(employee.getNickname()));
    }

    @Test
    public void testEmployeeUpdateProduct() {
        ObjectId id = new ObjectId();
        Product product = new Product(id);
        RestProductModel restProductModel = new RestProductModel();

        restProductModel.setProductId("242537222");
        restProductModel.setQuantity(654);
        restProductModel.setProductName("books");

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        String updatedProduct = employeeController.updateProduct(product.getHexId(), restProductModel);

        verify(productRepository).findById(product.getId());

        assertNotNull(product.getProductName());
        assertTrue(updatedProduct.contains(product.toString()));
    }

    @Test
    public void testDeleteEmployeeById() {
        ObjectId id = new ObjectId();
        Employee employee = new Employee(id);
        employee.setUserName("fred");
        List<Employee> employeeList = new LinkedList<>();
        employeeList.add(employee);

        when(employeeRepository.findAll()).thenReturn(employeeList);

        //employeeController.deleteById(id);
        verify(employeeRepository).deleteById(id);

        assertEquals(0, employeeRepository.findAll().size());
    }


}
