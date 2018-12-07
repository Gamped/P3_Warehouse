package dk.aau.cs.ds303e18.p3warehouse.managers;

import dk.aau.cs.ds303e18.p3warehouse.models.users.*;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ClientRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class CustomerManager {
    @Autowired
    private static ClientRepository clientRepository;
    @Autowired
    private static PublisherRepository publisherRepository;

    public static Customer getCustomerFromUser(User user){
        if(user.getUserType().equals(UserType.CLIENT)){
             return clientRepository.findById(user.getId()).orElse(null);
        }
        else if(user.getUserType().equals(UserType.PUBLISHER)){
            return publisherRepository.findById(user.getId()).orElse(null);
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
