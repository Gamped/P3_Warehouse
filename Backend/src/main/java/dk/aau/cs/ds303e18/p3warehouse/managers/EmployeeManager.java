package dk.aau.cs.ds303e18.p3warehouse.managers;

import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestEmployeeModel;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Employee;
import dk.aau.cs.ds303e18.p3warehouse.models.users.User;
import dk.aau.cs.ds303e18.p3warehouse.repositories.EmployeeRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class EmployeeManager {
    @Autowired
    private static EmployeeRepository employeeRepository;
    @Autowired
    private static UserRepository userRepository;

    public static Employee saveEmployeeToDb(Employee employee){
        User user = new User(employee.getId());
        user.copyFrom(employee);
        userRepository.save(user);
        return employeeRepository.save(employee);
    }
    public void removeEmployeeFromDb(ObjectId id){
        userRepository.deleteById(id);
        employeeRepository.deleteById(id);
    }
}
