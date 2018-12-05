package dk.aau.cs.ds303e18.p3warehouse.managers;

import dk.aau.cs.ds303e18.p3warehouse.repositories.ClientRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ClientManager {
    @Autowired
    private static ClientRepository clientRepository;
    @Autowired
    private static PublisherRepository publisherRepository;
}
