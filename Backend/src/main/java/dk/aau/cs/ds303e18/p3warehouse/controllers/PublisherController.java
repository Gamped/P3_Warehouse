package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.users.Publisher;
import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestPublisherModel;
import dk.aau.cs.ds303e18.p3warehouse.repositories.PublisherRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class PublisherController {

    @Autowired
    PublisherRepository publisherRepository;

    @GetMapping("/publishers")
    private Iterable<Publisher> all() {
        return publisherRepository.findAll();
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/publishers/new", consumes = "application/json")
    private String newPublisher(@RequestBody RestPublisherModel restPublisherModel) {
        ObjectId id = new ObjectId();
        Publisher newPublisher = new Publisher(id);
        BeanUtils.copyProperties(restPublisherModel, newPublisher);

        return "Saved " + newPublisher.getCompanyName();
    }

    @GetMapping("/publishers/{id}")
    Optional<Publisher> findById(@PathVariable String hexId) {
        ObjectId id = new ObjectId(hexId);
        return publisherRepository.findById(id);
    }

}
