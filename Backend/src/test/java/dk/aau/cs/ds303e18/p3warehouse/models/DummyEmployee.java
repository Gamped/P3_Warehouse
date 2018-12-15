package dk.aau.cs.ds303e18.p3warehouse.models;

import dk.aau.cs.ds303e18.p3warehouse.models.users.Employee;
import dk.aau.cs.ds303e18.p3warehouse.models.users.UserType;
import org.bson.types.ObjectId;

public class DummyEmployee {
    public static Employee makeDummyEmployee(int i){
        Employee employee = new Employee(new ObjectId());
        employee.setUserName("eUsername" + i );
        employee.setPassword("password" + i );
        employee.setNickname("nickname " + i);
        employee.setUserType(UserType.EMPLOYEE);

        return employee;
    }
}
