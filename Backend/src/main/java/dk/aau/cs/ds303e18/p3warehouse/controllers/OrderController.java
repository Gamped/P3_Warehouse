package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.repositories.OrderRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class OrderController {
    @Autowired
    OrderRepository orderRepository;

    @GetMapping("/orders")
    private Iterable<Order> findAll(){return orderRepository.findAll();}

    @PostMapping("/orders")
    private Order newOrder(@RequestBody Order newOrder){
        Order orderToSave = new Order(new ObjectId());
        orderToSave.copyParametersFrom(newOrder);
        return orderRepository.save(orderToSave);
    }

    @GetMapping("/orders/{id}")
    private Optional<Order> findById(@PathVariable ObjectId id){return orderRepository.findById(id);}

    @PutMapping("/orders")
    Order updateOrder(@RequestBody Order updatedOrder){
        Order query = orderRepository.findById(updatedOrder.getId()).orElse(null);
        if(query.getId() == null){
            return null;
        }
        else{
            return orderRepository.save(updatedOrder);
        }
    }

    @DeleteMapping("/orders/{id}")
    void deleteOrder(@RequestBody Order orderToDelete){orderRepository.delete(orderToDelete);}



}
