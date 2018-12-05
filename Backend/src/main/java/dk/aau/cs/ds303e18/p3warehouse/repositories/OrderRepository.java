package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Customer;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.web.JsonPath;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

public interface OrderRepository extends MongoRepository<Order, ObjectId> {
    Collection<Order> findAllByOwner();
}
