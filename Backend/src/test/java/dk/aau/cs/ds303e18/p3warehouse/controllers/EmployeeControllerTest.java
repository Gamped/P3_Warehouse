package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestProductModel;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Employee;
import dk.aau.cs.ds303e18.p3warehouse.models.users.UserType;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ClientRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.EmployeeRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ProductRepository;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
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

    @Test
    public void employeeControllerLoads() throws Exception {
        assertThat(employeeController).isNotNull();
    }

    @Before
    public void start() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testEmployeeCreateProduct() {
        ObjectId customerId = new ObjectId();

        Client client = new Client(customerId);

        RestProductModel restProductModel = new RestProductModel();
        restProductModel.setProductName("Cycling news");
        restProductModel.setQuantity(40);
        client.setUserType(UserType.CLIENT);

        String createdString = employeeController.createProduct(client.getHexId(),
                String.valueOf(client.getUserType()), restProductModel);

        assertEquals("Created!", createdString);
    }

    @Test
    public void testFindAllEmployees() {
        ObjectId id = new ObjectId();
        ObjectId objectId = new ObjectId();
        ObjectId employeeId = new ObjectId();

        Employee employee = new Employee(id);
        Employee secondEmployee = new Employee(objectId);
        Employee thirdEmployee = new Employee(employeeId);

        List<Employee> employeeList = new LinkedList<>();
        employeeList.add(employee);
        employeeList.add(secondEmployee);
        employeeList.add(thirdEmployee);

        when(employeeRepository.findAll()).thenReturn(employeeList);

        Collection<Employee> employees = employeeRepository.findAll();
        verify(employeeRepository).findAll();

        assertEquals(3, employees.size());
        assertEquals(employeeList, employees);
    }

    @Test
    public void testFindEmployeeById() {
        ObjectId id = new ObjectId();
        Employee employee = new Employee(id);

        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        //Employee retrievedEmployee = employeeController.getOneEmployee(String.valueOf(id));
        verify(employeeRepository).findById(id);
        //assertEquals(employee.getId(), retrievedEmployee.getId());
    }

    @Test
    public void testNewEmployee() {
        ObjectId id = new ObjectId();
        Employee employee = new Employee(id);

        when(employeeRepository.save(employee)).thenReturn(employee);

        //employeeController.newEmployee(employee);

        verify(employeeRepository).save(employee);

        assertSame(id, employee.getId());
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
