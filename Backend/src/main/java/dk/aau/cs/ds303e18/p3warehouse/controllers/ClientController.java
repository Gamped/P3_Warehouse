package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.users.*;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ProductRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.web.bind.annotation.*;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ClientRepository;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


import java.util.List;
import java.util.Optional;




@RestController
public class ClientController {

    @Autowired
    ClientRepository clientRepository;

    @GetMapping("/clients")
    private Iterable<Client> all() {
        return clientRepository.findAll();
    }

    @GetMapping("/clients/{id}")
    Optional<Client> findById(@PathVariable ObjectId databaseId) {

        return clientRepository.findById(databaseId);
    }


    @PostMapping("/clients")
    void save(@RequestBody Client newClient) {
        clientRepository.save(newClient);
    }


    @GetMapping("/clients/publisher/{id}")
    Publisher findByPublisherId(@PathVariable ObjectId publisherId) {
        return clientRepository.findByPublisherId(publisherId);
    }

    //Method is not implemented yet

    @PutMapping("/clients/{id}")
    Client updateClient(@PathVariable ObjectId databaseId, @RequestBody Client client) {
        Optional<Client> optClient = clientRepository.findById(databaseId);

        Client c = optClient.get();

        ContactInformation contactInformation = client.getContactInformation();

        ContactInformation ci = c.getContactInformation();
        if(ci.getEmail() != null) {
            ci.setEmail(contactInformation.getEmail());
        }

        clientRepository.save(c);
        return c;

    }

}