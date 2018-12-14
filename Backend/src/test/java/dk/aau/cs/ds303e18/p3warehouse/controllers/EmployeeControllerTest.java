package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.*;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static dk.aau.cs.ds303e18.p3warehouse.models.DummyClient.makeDummyClient;
import static dk.aau.cs.ds303e18.p3warehouse.models.DummyEmployee.makeDummyEmployee;
import static dk.aau.cs.ds303e18.p3warehouse.models.DummyProduct.makeDummyProduct;
import static dk.aau.cs.ds303e18.p3warehouse.models.DummyPublisher.makeDummyPublisher;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {

    @Autowired
    EmployeeController employeeController;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    PublisherRepository publisherRepository;

    @Autowired
    UserRepository userRepository;

    @Before
    public void start() {
        userRepository.deleteAll();
        employeeRepository.deleteAll();
        publisherRepository.deleteAll();
        clientRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void testEmployeeCreatePublisherProduct() {
        Publisher publisher = makeDummyPublisher(0, new ObjectId());
        Product product = makeDummyProduct(0, publisher);
        RestProductModel restProductModel = new RestProductModel();

        BeanUtils.copyProperties(product, restProductModel);
        publisher.addProduct(product);

        publisherRepository.save(publisher);
        employeeController.createProduct(publisher.getHexId(), publisher.getUserType().name(), restProductModel);

        assertTrue(publisherRepository.findById(publisher.getHexId()).get()
                .getProductStream()
                .anyMatch(x -> x.getProductName().equals(product.getProductName())));
    }

    @Test
    public void testEmployeeCreateClientProduct() {
        Client client = makeDummyClient(0);
        Product product = makeDummyProduct(0, client);
        RestProductModel restProductModel = new RestProductModel();

        BeanUtils.copyProperties(product, restProductModel);
        client.addProduct(product);

        clientRepository.save(client);
        employeeController.createProduct(client.getHexId(), client.getUserType().name(), restProductModel);

        assertTrue(clientRepository.findById(client.getHexId())
                .getProductStream()
                .anyMatch(x -> x.getProductName().equals(product.getProductName())));
    }

    @Test
    public void testEmployeeCreatePublisher() {
        Publisher publisher = makeDummyPublisher(0, new ObjectId());
        RestPublisherModel restPublisherModel = new RestPublisherModel();
        BeanUtils.copyProperties(publisher, restPublisherModel);

        employeeController.createPublisher(restPublisherModel);

        assertEquals(publisher.getContactInformation().getNickName(),
                publisherRepository.findByUserName(publisher.getUserName())
                        .getContactInformation().getNickName());
    }

    @Test
    public void testEmployeeCreateClient() {
        Client client = makeDummyClient(0);
        RestClientModel restClientModel = new RestClientModel();
        BeanUtils.copyProperties(client, restClientModel);

        employeeController.createClient(restClientModel);

        assertEquals(client.getContactInformation().getNickName(),
                clientRepository.findByUserName(client.getUserName())
                        .getContactInformation().getNickName());
    }

    @Test
    public void testEmployeeFindAllProducts() {
        Publisher publisher = makeDummyPublisher(0, new ObjectId());
        publisherRepository.save(publisher);
        for(int i = 0; i < 5; ++i){
            productRepository.save(makeDummyProduct(i, publisher));
        }

        assertEquals(5, employeeController.findAllProducts().size());
    }

    @Test
    public void testEmployeeFindAllUsers() {
        for(int i = 0; i < 5 ; ++i){
            Employee employee = makeDummyEmployee(i);
            employeeRepository.save(employee);
            userRepository.save(employee);
        }
        for(int i = 0; i < 5; ++i){
            Publisher publisher = makeDummyPublisher(i, new ObjectId());
            publisherRepository.save(publisher);
            userRepository.save(publisher);
        }
        for(int i = 0; i < 5; ++i){
            Client client = makeDummyClient(i);
            clientRepository.save(client);
            userRepository.save(client);
        }

        assertEquals(15, employeeController.findAllUsers().size());
    }

    @Test
    public void testFindEmployeeById() {
        Employee employee = makeDummyEmployee(0);
        employeeRepository.save(employee);

        assertEquals(employee.getHexId(), employeeController.findEmployeeById(employee.getHexId()).getHexId());
    }

    @Test
    public void testEmployeeFindProductById() {
        Publisher publisher = makeDummyPublisher(0, new ObjectId());
        Product product = makeDummyProduct(0, publisher);
        publisher.addProduct(product);

        productRepository.save(product);
        publisherRepository.save(publisher);

        assertEquals(0, employeeController.findProductById(product.getHexId()).get().getQuantity());
    }

    @Test
    public void testUpdateEmployee() {
        Employee employee = makeDummyEmployee(0);
        RestEmployeeModel restEmployeeModel = new RestEmployeeModel();

        employeeRepository.save(employee);
        userRepository.save(employee);

        BeanUtils.copyProperties(employee, restEmployeeModel);
        restEmployeeModel.setNickname("Bazanga");

        employeeController.updateEmployee(employee.getHexId(), restEmployeeModel);

        assertTrue(employeeRepository.findByUserName(employee.getUserName())
                .getNickname().equals(restEmployeeModel.getNickname()));
    }

    @Test
    public void testEmployeeUpdateProduct() {
        Publisher publisher = makeDummyPublisher(0, new ObjectId());
        Product product = makeDummyProduct(0, publisher);
        publisher.addProduct(product);

        publisherRepository.save(publisher);
        productRepository.save(product);

        RestProductModel restProductModel = new RestProductModel();
        BeanUtils.copyProperties(restProductModel, product);
        restProductModel.setQuantity(999);

        employeeController.updateProduct(product.getHexId(), restProductModel);

        assertEquals(999, productRepository.findById(product.getId()).get().getQuantity());
    }

    @Test
    public void testEmployeeUpdateProductInPublisher(){ //Same as above, except it checks if the product stored in publisher is also updated
        Publisher publisher = makeDummyPublisher(0, new ObjectId());
        Product product = makeDummyProduct(0, publisher);
        publisher.addProduct(product);

        publisherRepository.save(publisher);
        productRepository.save(product);

        RestProductModel restProductModel = new RestProductModel();
        BeanUtils.copyProperties(restProductModel, product);
        restProductModel.setQuantity(999);

        employeeController.updateProduct(product.getHexId(), restProductModel);
        assertTrue(publisherRepository.findById(publisher.getHexId()).get()
                .getProductStream()
                .anyMatch(x -> x.getQuantity() == 999));
    }

    @Test
    public void testEmployeeUpdateClientContactInformation() {
        Client client = makeDummyClient(0);
        clientRepository.save(client);

        ContactInformation contactInformation = new ContactInformation();
        BeanUtils.copyProperties(client, contactInformation);
        contactInformation.setNickName("rawass");

        employeeController.updateContactInformationOnClient(client.getHexId(), contactInformation);

        assertTrue  (clientRepository.findById(client.getHexId()).getContactInformation().getNickName()
                    .equals(contactInformation.getNickName()));
    }

    @Test
    public void testEmployeeUpdatePublisherContactInformation() {
        Publisher publisher = makeDummyPublisher(0, new ObjectId());
        publisherRepository.save(publisher);

        ContactInformation contactInformation = new ContactInformation();
        BeanUtils.copyProperties(publisher, contactInformation);
        contactInformation.setNickName("rawass");

        employeeController.updateContactInformationOnPublisher(publisher.getHexId(), contactInformation);

        assertTrue  (publisherRepository.findById(publisher.getHexId()).get().getContactInformation().getNickName()
                .equals(contactInformation.getNickName()));
    }

    @Test
    public void testUpdateUserCredentials() {
        Employee employee = makeDummyEmployee(0);
        employeeRepository.save(employee);
        userRepository.save(employee);

        RestUserModel restUserModel = new RestUserModel();
        BeanUtils.copyProperties(employee, restUserModel);
        restUserModel.setPassword("dankness");

        employeeController.updateUserCredentials(employee.getHexId(), restUserModel);
        assertTrue(userRepository.findById(employee.getId()).get().getPassword().equals(restUserModel.getPassword()));
    }

    @Test
    public void testDeleteEmployeeById() {
        Employee employee = makeDummyEmployee(0);
        employeeRepository.save(employee);
        userRepository.save(employee);

        employeeController.deleteEmployeeById(employee.getHexId());

        assertEquals(0, employeeRepository.findAll().size());
    }

    @Test
    public void testDeleteEmployeeAndUserById() {
        Employee employee = makeDummyEmployee(0);
        employeeRepository.save(employee);
        userRepository.save(employee);

        employeeController.deleteEmployeeById(employee.getHexId());

        assertEquals(0, userRepository.findAll().size());
    }

    @Test
    public void testEmployeeDeleteProductById() {
        Publisher publisher = makeDummyPublisher(0, new ObjectId());
        Product product = makeDummyProduct(0, publisher);
        publisher.addProduct(product);

        publisherRepository.save(publisher);
        productRepository.save(product);

        employeeController.deleteProductById(product.getHexId());

        assertEquals(0, productRepository.findAll().size());
    }

    @Test
    public void testEmployeeDeleteProductByIdFromPublisher() {
        Publisher publisher = makeDummyPublisher(0, new ObjectId());
        Product product = makeDummyProduct(0, publisher);
        publisher.addProduct(product);

        publisherRepository.save(publisher);
        productRepository.save(product);

        employeeController.deleteProductById(product.getHexId());

        assertEquals(0, publisherRepository.findById(publisher.getHexId()).get()
                .getProductStream().toArray().length);
    }
}
