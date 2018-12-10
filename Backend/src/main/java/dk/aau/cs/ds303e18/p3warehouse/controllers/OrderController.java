package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.MailService.OrderInfoMail;
import dk.aau.cs.ds303e18.p3warehouse.managers.CustomerManager;
import dk.aau.cs.ds303e18.p3warehouse.managers.OrderManager;
import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.models.users.UserType;
import dk.aau.cs.ds303e18.p3warehouse.repositories.*;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Customer;

import java.util.Collection;

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
    Order createOrder(@PathVariable("userHexId") String userHexId, @PathVariable("userType") String userType,
                       @RequestBody Order order) {
        Customer orderOwner = CustomerManager.getCustomerFromIdAndType(new ObjectId(userHexId), UserType.valueOf(userType));
        Order newOrder = new Order(new ObjectId());
        BeanUtils.copyProperties(order, newOrder);
        newOrder.setOwner(orderOwner);
        return OrderManager.saveOrderToDB(newOrder);
    }

    @GetMapping("/employee/orders")
    Collection<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @DeleteMapping("/orders/delete/{hexId}")
    void finishOrder(@PathVariable String hexId) {
        OrderInfoMail confimationSender = new OrderInfoMail("4N Mailhouse");


        confimationSender.sendOrderMsg(hexId.toString(), "mathiasgam@gmail.com");
        orderRepository.deleteById(new ObjectId(hexId));
    }

}
