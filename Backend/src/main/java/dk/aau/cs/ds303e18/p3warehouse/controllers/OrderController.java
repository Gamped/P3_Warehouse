package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.exceptions.InvalidQuantityException;
import dk.aau.cs.ds303e18.p3warehouse.MailService.OrderInfoMail;
import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.models.orders.OrderLine;
import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestOrderModel;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Customer;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Publisher;
import dk.aau.cs.ds303e18.p3warehouse.models.users.UserType;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import dk.aau.cs.ds303e18.p3warehouse.repositories.*;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
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
                       @RequestBody RestOrderModel order) {

        userType = userType.toUpperCase();
        Customer owner = null;

        try{
            switch(UserType.valueOf(userType)){
                case CLIENT:
                    owner = clientRepository.findById(new ObjectId(userHexId)).orElseThrow(() -> new Exception(userHexId));
                    break;
                case PUBLISHER:
                    owner = publisherRepository.findById(new ObjectId(userHexId)).orElseThrow(() -> new Exception(userHexId));
                    break;
                default:
                    return "Bad usertype";
            }
        } catch(Exception e) {
            return "User not found on id: " + userHexId;
        }
        int highestOrderNumber = 0;
        for(Order o : orderRepository.findAll()){
            if(o.getOrderId() > highestOrderNumber) highestOrderNumber = o.getOrderId();
        }

        Order newOrder = new Order(new ObjectId());
        BeanUtils.copyProperties(order, newOrder);
        newOrder.setOrderId(highestOrderNumber + 1);
        owner.addOrder(newOrder);
        newOrder.setOwner(owner);
        ArrayList<OrderLine> updatedOrderLines = new ArrayList<>();

        System.out.println("OrderLines " + order.getOrderLines());
        System.out.println("Copied orderLines " + newOrder.getOrderLines());

        if(!(order.getOrderLines().size() > 0)){
            return "No orderlines";
        }

        try {
            for (OrderLine x : order.getOrderLines()) {
                Product orderLineProduct = null;
                try {
                    orderLineProduct = productRepository.findById(new ObjectId(x.getProductHexId())).orElseThrow(() -> new Exception(x.getProductHexId()));
                }
                catch (Exception e){
                    return "Could not find product with id: " + x.getProductHexId();
                }
                 if (orderLineProduct.getQuantity() >= x.getQuantity()) {
                    orderLineProduct.subtract(x.getQuantity());
                    x.setProduct(orderLineProduct);
                    updatedOrderLines.add(x);
                    productRepository.save(orderLineProduct);
                } else {
                    throw new InvalidQuantityException(x.getProduct().getProductName());
                }
            }
        } catch (InvalidQuantityException e) {
            return "Cannot order more than stock in product: " + e.getMessage();
        }

        switch (owner.getUserType()) {
            case CLIENT:
                clientRepository.save((Client) owner);
                break;
            case PUBLISHER:
                publisherRepository.save((Publisher) owner);
                break;
        }
        orderRepository.save(newOrder);
        return "Created! " + newOrder.getHexId();
    }

    @PutMapping("/employee/orders/{hexId}")
    String updateOrder(@PathVariable String hexId, @RequestBody RestOrderModel responseBody){

        Order order;
        try {
            order = orderRepository.findById(new ObjectId(hexId)).orElseThrow(() -> new Exception());
        }
        catch(Exception e){
            return "Order not found on id: " + hexId;
        }

        Customer owner = null;
        try {
            switch (order.getOwner().getUserType()) {
                case CLIENT:
                    owner = clientRepository.findById(new ObjectId(order.getOwner().getHexId())).orElseThrow(() -> new Exception());
                    break;
                case PUBLISHER:
                    owner = publisherRepository.findById(new ObjectId(order.getOwner().getHexId())).orElseThrow(() -> new Exception());
                    break;
                default:
                    return "Bad usertype";
            }
        }catch (Exception e){
            return "Customer not found on id: " + order.getOwner().getHexId();
        }
        owner.removeOrder(order);
        try{
            order.addProductsBackToStock();
            BeanUtils.copyProperties(responseBody, order);
            System.out.println(order);
            order.subtractProductsFromStock();
        }
        catch (InvalidQuantityException e){
            return "Bad stock in orderline";
        }
        owner.addOrder(order);
        switch(order.getOwner().getUserType()){
            case CLIENT:
                clientRepository.save((Client)owner);
            break;
            case PUBLISHER:
                publisherRepository.save((Publisher)owner);
            break;
        }
        orderRepository.save(order);
        return "Updated order with id: " + order.getHexId();
    }

    @GetMapping("/employee/orders")
    Collection<Order> findAllOrders() {return orderRepository.findAll();}

    @DeleteMapping("/orders/delete/{hexId}")
    String finishOrder(@PathVariable String hexId) {

        // NOTE: You need a file called "mail.txt" on your desktop with the following: [gmail address];[password]
        // The gmail account needs to allow "less secure apps"
        OrderInfoMail confimationSender = new OrderInfoMail("4N Mailhouse");
        // Sends a mail to the email, however, this is currently a hardcoded email address
        confimationSender.sendOrderMsg(hexId.toString(), "mgampe17@student.aau.dk");

        Order queryedOrder = orderRepository.findById(new ObjectId(hexId)).orElse(null);

        if(queryedOrder != null){
            Customer owner = queryedOrder.getOwner();
            Collection<Product> updatedProducts = new ArrayList<>();
            try {
                owner.removeOrder(queryedOrder);
                updatedProducts = queryedOrder.getOrderLines().stream().map(x -> {
                    x.getProduct().setQuantity(x.getProduct().getQuantity() + x.getQuantity());
                    return x.getProduct();
                }).collect(Collectors.toCollection(ArrayList::new));
            } catch(Exception e){e.printStackTrace();}

            orderRepository.delete(queryedOrder);

            try {
                switch (owner.getUserType()) {
                    case CLIENT:
                        clientRepository.save((Client) owner);
                        break;
                    case PUBLISHER:
                        publisherRepository.save((Publisher) owner);
                        break;
                }
            } catch(Exception e){e.printStackTrace();}

            for(Product p : updatedProducts){
                productRepository.save(p);
            }
            orderRepository.deleteById(new ObjectId(hexId));
            return "Deleted";
        }
        return "Error: Failed Successfully";
    }
}

