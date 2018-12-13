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

import java.util.Collection;
import java.util.HashSet;
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

        Customer owner = null;

        try{
            switch(UserType.valueOf(userType)){
                case CLIENT: owner = clientRepository.findById(new ObjectId(userHexId)).orElseThrow(() -> new Exception(userHexId)); break;
                case PUBLISHER: owner = publisherRepository.findById(new ObjectId(userHexId)).orElseThrow(() -> new Exception(userHexId)); break;
            }
        }
        catch(Exception e){
            return "User not found on id: " + userHexId;
        }
        Order newOrder = new Order(new ObjectId());
        BeanUtils.copyProperties(order, newOrder);


        owner.addOrder(newOrder);
        newOrder.setOwner(owner);
        Collection<OrderLine> updatedOrderLines = new HashSet<>();
        try {
            for (OrderLine x : order.getOrderLines()) {
                Product dbProduct = productRepository.findById(x.getProduct().getId()).orElse(null);
                if (dbProduct.getQuantity() >= x.getQuantity()){
                    dbProduct.subtract(x.getQuantity());
                    x.setProduct(dbProduct);
                    updatedOrderLines.add(x);
                } else {
                    throw new InvalidQuantityException(x.getProduct().getProductName());
                }
            }
        } catch (InvalidQuantityException e) {
            return "Cannot order more than stock in product: " + e.getMessage();
        }

        productRepository.saveAll(updatedOrderLines.stream().map(x -> x.getProduct()).collect(Collectors.toSet()));
        switch (owner.getUserType()){
            case CLIENT: clientRepository.save((Client)owner); break;
            case PUBLISHER: publisherRepository.save((Publisher)owner); break;
        }
        orderRepository.save(newOrder);
        return "Created!";
    }

    @PutMapping("/employee/orders/{hexId}")
    private Order updateOrder(@PathVariable String hexId, @RequestBody RestOrderModel responseBody){
        Order order;
        try {
            order = orderRepository.findById(new ObjectId(hexId)).orElseThrow(() -> new Exception());
        }
        catch(Exception e){
            return null;
        }
        try{
            order.addProductsBackToStock();
            BeanUtils.copyProperties(responseBody, order);
            order.subtractProductsFromStock();
        }
        catch (InvalidQuantityException e){
            return null;
        }
        return OrderManager.saveOrderToDB(order);
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
            try {
                owner.removeOrder(queryedOrder);
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
            orderRepository.deleteById(new ObjectId(hexId));
            return "Order deleted?";
        }
        return "Error: Failed Successfully";
    }
}

