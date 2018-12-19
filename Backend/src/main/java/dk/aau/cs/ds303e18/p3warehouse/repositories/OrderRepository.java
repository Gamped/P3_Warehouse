package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;

public interface OrderRepository extends MongoRepository<Order, ObjectId> {
    Collection<Order> findAllByOwner();
}
