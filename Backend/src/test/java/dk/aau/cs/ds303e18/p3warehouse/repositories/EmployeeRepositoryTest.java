package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.models.users.Employee;
import dk.aau.cs.ds303e18.p3warehouse.models.users.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockEmployeeData.makeEmployee;
import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockEmployeeData.makeSecondEmployee;
import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockEmployeeData.makeThirdEmployee;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@DataMongoTest
public class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    UserRepository userRepository;

    @Before
    public void deleteAll() {
        employeeRepository.deleteAll();
    }

    @Test
    public void testFindEmployeeById() {
        Employee emp = makeEmployee();

        employeeRepository.save(emp);
        Employee retrievedEmployee = employeeRepository.findById(emp.getId()).orElse(null);
        assertEquals(emp.getId(), retrievedEmployee.getId());
    }

    @Test
    public void testFindInformation() {
        Employee emp = makeEmployee();

        employeeRepository.save(emp);
        Employee retrievedEmployee = employeeRepository.findById(emp.getId()).orElse(null);
        assertEquals(emp.getNickname(), retrievedEmployee.getNickname());
    }

    @Test
    public void testFindEmployeeByNickName() {
        Employee emp = makeEmployee();

        employeeRepository.save(emp);
        Employee retrievedEmployee = employeeRepository.findByNickname(emp.getNickname());
        assertEquals(emp.getHexId(), retrievedEmployee.getHexId());
    }

    @Test
    public void findAllEmployees() {
        Employee emp = makeEmployee();
        Employee emp2 = makeSecondEmployee();
        Employee emp3 = makeThirdEmployee();

        employeeRepository.save(emp);
        employeeRepository.save(emp2);
        employeeRepository.save(emp3);

        List<Employee> employeeList = employeeRepository.findAll();
        assertNotNull(employeeList);
        assertEquals(3, employeeList.size());
    }

    @Test
    public void testFindEmployeeByUserId() {
        Employee emp2 = makeEmployee();

        User user = new User(emp2.getId());
        user.copyFrom(emp2);

        employeeRepository.save(emp2);
        userRepository.save(user);

        User retrievedUser = userRepository.findById(user.getId()).orElse(null);
        Employee retrievedEmployee = employeeRepository.findById(retrievedUser.getId()).orElse(null);
        assertEquals(emp2.getHexId(), retrievedEmployee.getHexId());
    }

    @Test
    public void testDeleteEmployeeById() {
        Employee emp2 = makeEmployee();

        employeeRepository.save(emp2);
        employeeRepository.deleteById(emp2.getId());
        assertNull(employeeRepository.findById(emp2.getId()).orElse(null));
    }

    @Test
    public void testDeleteAllEmployees() {
        Employee emp2 = makeEmployee();
        Employee emp3 = makeSecondEmployee();

        employeeRepository.save(emp2);
        employeeRepository.save(emp3);

        employeeRepository.deleteAll();
        assertEquals(0, employeeRepository.findAll().size());
    }
}
