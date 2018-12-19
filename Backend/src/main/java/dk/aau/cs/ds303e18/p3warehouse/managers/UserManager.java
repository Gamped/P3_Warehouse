package dk.aau.cs.ds303e18.p3warehouse.managers;

import dk.aau.cs.ds303e18.p3warehouse.models.users.User;
import dk.aau.cs.ds303e18.p3warehouse.models.users.UserType;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ClientRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.EmployeeRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.PublisherRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

public class UserManager {
    @Autowired
    private static PublisherRepository publisherRepository;
    @Autowired
    private static ClientRepository clientRepository;
    @Autowired
    private static EmployeeRepository employeeRepository;

    public static User getUserFromIdAndType(ObjectId id, UserType userType){

        switch (userType){
            case CLIENT:
                return clientRepository.findById(id).get();
            case PUBLISHER:
                return publisherRepository.findById(id).get();
            case EMPLOYEE:
                return employeeRepository.findById(id).get();
        }
        return null;
    }
}
