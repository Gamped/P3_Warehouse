package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestClientModel;
import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestEmployeeModel;
import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestPublisherModel;
import dk.aau.cs.ds303e18.p3warehouse.models.users.*;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ClientRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.EmployeeRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.PublisherRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static dk.aau.cs.ds303e18.p3warehouse.models.DummyClient.makeDummyClient;
import static dk.aau.cs.ds303e18.p3warehouse.models.DummyEmployee.makeDummyEmployee;
import static dk.aau.cs.ds303e18.p3warehouse.models.DummyPublisher.makeDummyPublisher;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
    @Autowired
    UserController userController;
    @Autowired
    EmployeeController employeeController;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    PublisherRepository publisherRepository;
    @Autowired
    ClientRepository clientRepository;

    @Before
    public void start(){
        userRepository.deleteAll();
        employeeRepository.deleteAll();
        publisherRepository.deleteAll();
        clientRepository.deleteAll();
    }

    @Test
    public void testLogInEmployee(){
        Employee employee = makeDummyEmployee(0);
        RestEmployeeModel restEmployeeModel = new RestEmployeeModel();
        BeanUtils.copyProperties(employee, restEmployeeModel);
        employeeController.createEmployee(restEmployeeModel);

        assertEquals(userRepository.findAll().get(0).getId().toHexString(),
                userController.authenticateUser(employee.getUserName(), employee.getPassword()).getId().toHexString());
    }

    @Test
    public void testLogInPublisher(){ //Fails due to controller not being able to read employee's usertype or some shit.
        Publisher publisher = makeDummyPublisher(0, new ObjectId());
        RestPublisherModel restPublisherModel = new RestPublisherModel();
        BeanUtils.copyProperties(publisher, restPublisherModel);
        employeeController.createPublisher(restPublisherModel);

        assertEquals(userRepository.findAll().get(0).getId().toHexString(),
                userController.authenticateUser(publisher.getUserName(), publisher.getPassword()).getId().toHexString());
    }

    @Test
    public void testLogInClient(){
        Client client = makeDummyClient(0);
        RestClientModel restClientModel = new RestClientModel();
        BeanUtils.copyProperties(client, restClientModel);
        employeeController.createClient(restClientModel);

        assertEquals(userRepository.findAll().get(0).getId().toHexString(),
                userController.authenticateUser(client.getUserName(), client.getPassword()).getId().toHexString());
    }
}
