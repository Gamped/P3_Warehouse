package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.CustomException.InvalidQuantityException;
import dk.aau.cs.ds303e18.p3warehouse.MailService.OrderInfoMail;
import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.models.orders.OrderLine;
import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestOrderLineModel;
import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestOrderModel;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Publisher;
import dk.aau.cs.ds303e18.p3warehouse.models.users.User;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import dk.aau.cs.ds303e18.p3warehouse.repositories.*;
import org.bson.types.ObjectId;
import org.omg.CORBA.DynAnyPackage.Invalid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.security.acl.Owner;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RequestMapping("/api")
@RestController
public class OrderController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    PublisherRepository publisherRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;

    @PostMapping("/orders/{userHexId}/{userType}")
    String createOrder(@PathVariable("userHexId") String userHexId, @PathVariable("userType") String userType,
                       @RequestBody Order order) {

        Optional<Publisher> optionalPublisher = publisherRepository.findById(new ObjectId(userHexId));
        Publisher publisher = optionalPublisher.get();
        Order newOrder = new Order(new ObjectId());
        BeanUtils.copyProperties(order, newOrder);


        publisher.addOrder(order);
        order.setOwner(publisher);
        Collection<OrderLine> updatedOrderLines = new HashSet<>();
        try {
            for (OrderLine x : order.getOrderLines()) {
                if (x.getProduct().getQuantity() >= x.getQuantity()) {
                    x.getProduct().subtract(x.getQuantity());
                    updatedOrderLines.add(x);
                } else {
                    throw new InvalidQuantityException(x.getProduct().getProductName());
                }
            }
        } catch (InvalidQuantityException e) {
            return "Cannot order more than stock in product: " + e.getMessage();
        }

        productRepository.saveAll(updatedOrderLines.stream().map(x -> x.getProduct()).collect(Collectors.toSet()));
        publisherRepository.save(publisher);
        orderRepository.save(order);

        return "Created!";
    }

    @GetMapping("/employee/orders")
    Collection<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @DeleteMapping("/orders/delete/{hexId}")
    void finishOrder(@PathVariable String hexId) {
        OrderInfoMail confimationSender = new OrderInfoMail("4N Mailhouse");

    }
}

