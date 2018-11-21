package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.users.ContactInformation;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Publisher;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ClientRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.PublisherRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
public class PublisherController {

    @Autowired
    PublisherRepository publisherRepository;
    ClientRepository clientRepository;

    @GetMapping("/publishers")
    private Iterable<Publisher> all() {
        return publisherRepository.findAll();
    }

    @PostMapping("/publishers")
    private Publisher newPublisher(@RequestBody Publisher newPublisher) {
        return publisherRepository.save(newPublisher);
    }

    @GetMapping("/publishers/{id}")
    Optional<Publisher> findById(@PathVariable String hexId) {
        ObjectId id = new ObjectId(hexId);
        return publisherRepository.findById(id);
    }

    //Not sure this will work yet
    @GetMapping("/publishers/{clients}")
    Publisher findByPublisherId(@PathVariable ObjectId publisherId) {
        return clientRepository.findByPublisherId(publisherId);
    }


}
