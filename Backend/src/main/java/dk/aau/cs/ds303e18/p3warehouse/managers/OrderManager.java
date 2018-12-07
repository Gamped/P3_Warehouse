package dk.aau.cs.ds303e18.p3warehouse.managers;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Customer;
import dk.aau.cs.ds303e18.p3warehouse.repositories.OrderRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderManager {
    @Autowired
    private static OrderRepository orderRepository;

    public static Order saveOrderToDB(Order newOrder){
        Customer owner = newOrder.getOwner();
        owner.addOrder(newOrder);
        CustomerManager.saveCustomerToDatabase(owner);
        return orderRepository.save(newOrder);
    }

    public static void deleteOrderFromDB(ObjectId id){
        Order order = orderRepository.findById(id).get();
        Customer owner = order.getOwner();
        owner.removeOrder(order);
        orderRepository.deleteById(id);
    }
}
