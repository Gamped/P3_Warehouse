package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataMongoTest
public class OrderRepositoryTest {
    @Autowired
    OrderRepository repository;

    @Test
    public void findByIdTest(){
        Order order = new Order(new ObjectId());
        order.setTitle("ID test 1");
        System.out.println("Object ID: " + order.getId());
        repository.save(order);
    }
}
