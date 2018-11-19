package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.users.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ClientRepository;

import java.util.Optional;

@RestController
public class ClientController {

    @Autowired
    ClientRepository clientRepository;

    @GetMapping("/clients")
    private Iterable<IClient> all() {
        return clientRepository.findAll();
    }

    @PostMapping("/clients")
    private IClient newClient(@RequestBody IClient newClient) {
        return clientRepository.save(newClient);
    }

    @GetMapping("/clients/{id}")
    IClient findClient(@PathVariable ObjectId databaseId) {
        return clientRepository.findById(databaseId);
    }

    @GetMapping("/clients/{companyName}")
    Client one(@PathVariable String companyName) {
        return clientRepository.findById(companyName);
    }

    @PutMapping("/clients/{companyName}")
    Client editCompanyName(@RequestBody Client newClient, @PathVariable String companyName) {
        return clientRepository.findById(companyName)
                .map(client -> client.setCompanyName(newClient.getCompanyName()));
    }

    @GetMapping("/clients/{publisher}")
    Client one(@PathVariable String publisher) {
        return clientRepository.findById(publisher);
    }

    @PutMapping("/clients/{id}")
    IClient updateClient(@PathVariable ObjectId databaseId, @RequestBody IClient client) {
        Optional<IClient> optClient = clientRepository.findById(databaseId);
        IContactInformation contactInformation = client.getContactInformation();
        IClient c = optClient.get();
        IContactInformation ci = c.getContactInformation();
        if(ci.getEmail() != null) {
            ci.setEmail(contactInformation.getEmail());
        }
        if (ci.getAddress() != null) {
            ci.setAddress(contactInformation.getAddress());
        }
        if (ci.getPhoneNumber() != null){
            ci.setPhoneNumber(contactInformation.getPhoneNumber());
        }
        if (ci.getZipCode() != null) {
            ci.setZipCode(contactInformation.getZipCode());
        }

        clientRepository.save(c);
        return c;

    }




}