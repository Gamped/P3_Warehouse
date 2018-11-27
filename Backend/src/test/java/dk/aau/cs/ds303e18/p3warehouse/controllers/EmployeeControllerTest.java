package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.users.Employee;
import dk.aau.cs.ds303e18.p3warehouse.repositories.EmployeeRepository;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {

    @InjectMocks
    EmployeeController employeeController;

    @Mock
    EmployeeRepository employeeRepository;

    @Test
    public void employeeControllerLoads() throws Exception {
        assertThat(employeeController).isNotNull();
    }

    @Before
    public void start() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDeleteEmployeeById() {
        ObjectId id = new ObjectId();
        Employee employee = new Employee(id);

        when(employeeRepository.findById(id)).thenReturn(Optional.ofNullable(employee));

        employeeController.deleteById(id);
        verify(employeeRepository).deleteById(id);
        List<Employee> employees = new LinkedList<>();
        assertEquals(employees, employeeRepository.findAll());
    }

    @Test
    public void testFindEmployeeById() {
        ObjectId id = new ObjectId();
        Employee employee = new Employee(id);

        when(employeeRepository.findById(id)).thenReturn(Optional.ofNullable(employee));

        //Optional<Employee> optEmployee = employeeController.findById(id);
       // Employee retrievedEmployee = optEmployee.get();

        //assertEquals(employee.getId(), retrievedEmployee.getId());
    }
}
