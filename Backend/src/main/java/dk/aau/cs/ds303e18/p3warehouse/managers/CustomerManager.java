package dk.aau.cs.ds303e18.p3warehouse.managers;

import dk.aau.cs.ds303e18.p3warehouse.models.users.*;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ClientRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.PublisherRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerManager {
    @Autowired
    private static ClientRepository clientRepository;
    @Autowired
    private static PublisherRepository publisherRepository;

    public static Customer getCustomerFromUser(User user){
        return getCustomerFromIdAndType(user.getId(), user.getUserType());
    }

    public static Customer getCustomerFromIdAndType(ObjectId id, UserType type){
        if(type.equals(UserType.CLIENT)){
            return clientRepository.findById(id).orElse(null);
        }
        else if(type.equals(UserType.PUBLISHER)){
            return publisherRepository.findById(id).orElse(null);
        }
        else{
            return null;
        }
    }

    public static Customer saveCustomerToDatabase(Customer customer){
        if(customer.getUserType().equals(UserType.CLIENT)){
            return clientRepository.save((Client) customer);
        }
        else if(customer.getUserType().equals(UserType.PUBLISHER)){
            return publisherRepository.save((Publisher) customer);
        }
        else{
            return null;
        }
    }
}
