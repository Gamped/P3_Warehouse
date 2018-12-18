package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.CustomException.InvalidQuantityException;
import dk.aau.cs.ds303e18.p3warehouse.MailService.OrderInfoMail;
import dk.aau.cs.ds303e18.p3warehouse.managers.OrderManager;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RequestMapping("/api")
@RestController
public class OrderController {

    //TODO: Delete order virker ikke
    //TODO: Create order queurier kun efter en publisher og er ikke optimalt. SKal de opdateres flere steder eller blot i orderRepo?
    //TODO: De andre virker heller ikke og er nødt til at blive testet med data.

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
                case CLIENT: owner = clientRepository.findById(new ObjectId(userHexId)).orElseThrow(() -> new Exception(userHexId)); break;
                case PUBLISHER: owner = publisherRepository.findById(new ObjectId(userHexId)).orElseThrow(() -> new Exception(userHexId)); break;
                default: return "Bad usertype";
            }
        }
        catch(Exception e){
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
                } else {
                    throw new InvalidQuantityException(x.getProduct().getProductName());
                }
            }
        } catch (InvalidQuantityException e) {
            return "Cannot order more than stock in product: " + e.getMessage();
        }

        for (OrderLine l : updatedOrderLines){
            productRepository.save(l.getProduct());
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
    String updateOrder(@PathVariable String hexId, @RequestBody RestOrderModel responseBody){ //privacy removed to allow unit testing.
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
    Collection<Order> findAllOrders() {
        return orderRepository.findAll();
    }



    @DeleteMapping("/orders/delete/{hexId}")
    String finishOrder(@PathVariable String hexId) {
        OrderInfoMail confimationSender = new OrderInfoMail("4N Mailhouse");
        confimationSender.sendOrderMsg(hexId.toString(), "jesus@himlen.dk");
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
            }
            catch(Exception e){

            }
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
            }
            catch(Exception e){

            }
            for(Product p : updatedProducts){
                productRepository.save(p);
            }
            orderRepository.deleteById(new ObjectId(hexId));
            return "Order deleted?";
        }
        return "Error: Failed Successfully";
    }


}

