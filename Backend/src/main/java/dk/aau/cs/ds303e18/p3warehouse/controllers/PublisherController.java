package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestPublisherModel;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Publisher;
import dk.aau.cs.ds303e18.p3warehouse.repositories.OrderRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ProductRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.PublisherRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/api")
@CrossOrigin
@RestController
public class PublisherController {

    @Autowired
    PublisherRepository publisherRepository;

    @GetMapping("/publishers")
    Iterable<Publisher> findAll() {

        return publisherRepository.findAll();
    }

    @GetMapping("/publishers/{id}")
    Optional<Publisher> findById(@PathVariable String id) {

        ObjectId objectId = new ObjectId(id);
        Optional<Publisher> publisher = publisherRepository.findById(objectId);

        return publisher;
    }

    @PostMapping("/publishers")
    Publisher newPublisher(@RequestBody RestPublisherModel restPublisher) {

        ObjectId id = new ObjectId();
        Publisher newPublisher = new Publisher(id);
        BeanUtils.copyProperties(restPublisher, newPublisher);

        return publisherRepository.save(newPublisher);
    }

    @PutMapping("/publishers/{hexId}")
    String update(@PathVariable("hexId") String hexId, @RequestBody RestPublisherModel restPublisher) {

        ObjectId id = new ObjectId(hexId);
        Optional<Publisher> optPublisher = publisherRepository.findById(id);
        Publisher publisherToSave = optPublisher.get();

        BeanUtils.copyProperties(restPublisher, publisherToSave);

        publisherRepository.save(publisherToSave);

        return "Publisher updated! \n" + publisherToSave.getUserName() + "\n" + publisherToSave.getHexId();
    }

    @DeleteMapping("/publishers/{id}")
    void delete(@PathVariable String hexId) {
        ObjectId id = new ObjectId(hexId);

        publisherRepository.deleteById(id);
    }

    @GetMapping("/publishers/{hexId}/products")
    Optional<Publisher> findPublisherInfoById(@PathVariable("hexId") String hexId) {

        ObjectId objectId = new ObjectId(hexId);
        return publisherRepository.findById(objectId);
    }

}
