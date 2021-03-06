package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestPublisherModel;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Publisher;
import dk.aau.cs.ds303e18.p3warehouse.models.users.User;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import dk.aau.cs.ds303e18.p3warehouse.repositories.OrderRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ProductRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.PublisherRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

import java.util.stream.Collectors;
import java.util.stream.Collector;
import java.util.stream.Stream;

@RequestMapping("/api")
@CrossOrigin
@RestController
public class PublisherController {

    @Autowired
    PublisherRepository publisherRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @GetMapping("/publishers")
    Iterable<Publisher> findAll() {
        return publisherRepository.findAll();
    }

    @GetMapping("/publishers/{id}")
    Publisher findById(@PathVariable String id) {
        ObjectId objectId = new ObjectId(id);
        return publisherRepository.findById(objectId).get();
    }

    @GetMapping("/publishers/{hexId}/orders")
    Stream<Order> getAllPublisherOrders(@PathVariable String hexId){
        Publisher publisher = publisherRepository.findById(new ObjectId(hexId)).orElse(null);
        return publisher.getOrderStream();
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
        User user = userRepository.findById(id).orElse(null);
        BeanUtils.copyProperties(publisherToSave, user);

        publisherRepository.save(publisherToSave);
        userRepository.save(user);
        return "Publisher updated! \n" + publisherToSave.getUserName() + "\n" + publisherToSave.getHexId();
    }

    @DeleteMapping("/publishers/{hexId}")
    void delete(@PathVariable String hexId) {
        ObjectId id = new ObjectId(hexId);
        Publisher publisher = publisherRepository.findById(id).orElse(null);

        publisher.getClientStream().map(x -> x.unassignAllProducts()).forEach(x -> x.forEach(productRepository::delete));
        publisher.getClientStream().map(x -> x.unassignAllOrders()).forEach(x -> x.forEach(orderRepository::delete));
        publisher.unassignAllOrders().forEach(orderRepository::delete);
        publisher.unassignAllProducts().forEach(productRepository::delete);
        publisherRepository.delete(publisher);
        User user = userRepository.findById(publisher.getId()).orElse(null);
        userRepository.delete(user);
    }

    @GetMapping("/publishers/{hexId}/products")
    ArrayList<Product> findPublisherProducts(@PathVariable("hexId") String hexId) {

        Publisher publisher;

        ObjectId objectId = new ObjectId(hexId);
        try {

            publisher = publisherRepository.findById(objectId).orElseThrow(() -> new Exception());
        } catch(Exception e) {

            return null;
        }

        ArrayList<Product> allProducts = new ArrayList<>();

        if (publisher.getProductStream() != null) {

            for (Product product : publisher.getProductStream().collect(Collectors.toList())) {

                allProducts.add(product);
            }
        }

        if (publisher.getNumberOfClients() != 0) {

            for (Client client : publisher.getClientStream().collect(Collectors.toList())) {

                if (client.getProductStream() != null) {

                    for (Product product : client.getProductStream().collect(Collectors.toList())) {
                        allProducts.add(product);
                    }
                }

            }
        }
        return allProducts;
    }

}

