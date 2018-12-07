package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestPublisherModel;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Publisher;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import dk.aau.cs.ds303e18.p3warehouse.repositories.OrderRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ProductRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.PublisherRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequestMapping("/api")
@CrossOrigin
@RestController
public class PublisherController {

    @Autowired
    PublisherRepository publisherRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;

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

    /*@GetMapping("/publishers/clients/products")
    Iterable<Product> findAllClientsProducts(@RequestBody Publisher publisher) {
        Stream<Client> clientStream = publisher.getClientStream();

        //return productRepository.findByOwner(clientStream.iterator().next());
    }

    @GetMapping("/publishers/client/{hexId}/products")
    Iterable<Product> findProductsByClientId(@PathVariable("hexId") String hexId) {
        ObjectId id = new ObjectId(hexId);
        Client client = new Client(id);

        return productRepository.findByOwner(client);
    }

    @GetMapping("/publishers/clients/orders")
    Iterable<Order> findAllClientOrders(@RequestBody Publisher publisher) {
        Stream<Client> clients = publisher.getClientStream();

        return orderRepository.findByOwner(clients.iterator().next());
    }

    @GetMapping("/publishers/client/{clientId}/order")
    Iterable<Order> findOneClientOrders(@RequestBody Client client) {

        return orderRepository.findByOwner(client);
    }*/
    @GetMapping("/publishers/products/{userType}/{hexId}")
    Optional<Publisher> findAllProductsOnPublisher(@PathVariable("userType") String userType,
                                                   @PathVariable("hexId") String hexId) {

        ObjectId objectId = new ObjectId(hexId);
        return publisherRepository.findById(objectId);
    }

}
