package dk.aau.cs.ds303e18.p3warehouse.systemTest;

import dk.aau.cs.ds303e18.p3warehouse.models.users.Employee;
import dk.aau.cs.ds303e18.p3warehouse.models.users.UserType;
import org.bson.types.ObjectId;

public class MakeMockEmployeeData {

    public static Employee makeEmployee() {
        Employee employee = new Employee(new ObjectId());
        employee.setUserType(UserType.EMPLOYEE);
        employee.setUserName("mads");
        employee.setNickname("Mads");
        employee.setPassword("5686");

        return employee;
    }

    public static Employee makeSecondEmployee() {
        Employee employee = new Employee(new ObjectId());
        employee.setUserType(UserType.EMPLOYEE);
        employee.setUserName("hans");
        employee.setNickname("Hans");
        employee.setPassword("5426");

        return employee;
    }

    public static Employee makeThirdEmployee() {
        Employee employee = new Employee(new ObjectId());
        employee.setNickname("Jane");
        employee.setUserType(UserType.EMPLOYEE);
        employee.setPassword("123");
        employee.setUserName("jane");

        return employee;
    }

    public static Employee makeFourthEmployee() {
        Employee employee = new Employee(new ObjectId());
        employee.setNickname("Casper");
        employee.setUserType(UserType.EMPLOYEE);
        employee.setPassword("123");
        employee.setUserName("casper");

        return employee;
    }

    public static Employee makeFifthEmployee() {
        Employee employee = new Employee(new ObjectId());
        employee.setNickname("Steen");
        employee.setUserType(UserType.EMPLOYEE);
        employee.setPassword("123");
        employee.setUserName("steen");

        return employee;
    }

}
