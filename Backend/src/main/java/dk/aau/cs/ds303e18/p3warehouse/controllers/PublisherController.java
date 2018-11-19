package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.users.IContactInformation;
import dk.aau.cs.ds303e18.p3warehouse.models.users.IPublisher;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Publisher;
import dk.aau.cs.ds303e18.p3warehouse.repositories.PublisherRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class PublisherController {

    @Autowired
    PublisherRepository publisherRepository;

    @GetMapping("/publishers")
    private Iterable<IPublisher> all() {
        return publisherRepository.findAll();
    }

    @PostMapping("/publishers")
    private IPublisher newPublisher(@RequestBody IPublisher newPublisher) {
        return publisherRepository.save(newPublisher);
    }

    @GetMapping("/publishers/{companyName}")
    IPublisher one(@PathVariable String companyName) {
        return publisherRepository.findById(companyName);
    }

    @GetMapping("/publishers/{id}")
    IPublisher one(@PathVariable String id) {
        return publisherRepository.findById(id);
    }

    @GetMapping("/publishers/{clients}")
    IPublisher one(@PathVariable Collection<Client> clients) {
        return publisherRepository.findById(clients);
    }

    @GetMapping("/publishers/{contactInformation")
    IPublisher one(@PathVariable IContactInformation contactInformation) {
        return publisherRepository.findById(contactInformation);
    }

}
