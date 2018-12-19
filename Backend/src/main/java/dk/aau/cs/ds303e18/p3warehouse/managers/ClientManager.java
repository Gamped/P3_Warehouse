package dk.aau.cs.ds303e18.p3warehouse.managers;

import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.users.User;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ClientRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.PublisherRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ClientManager {
    @Autowired
    private static ClientRepository clientRepository;
    @Autowired
    private static PublisherRepository publisherRepository;
    @Autowired
    private static UserRepository userRepository;

    public static Client saveClientToDB(Client client){

        User user = new User(client.getId());
        user.copyFrom(client);
        userRepository.save(user);
        return clientRepository.save(client);
    }
}
