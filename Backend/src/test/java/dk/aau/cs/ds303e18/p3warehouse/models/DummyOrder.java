package dk.aau.cs.ds303e18.p3warehouse.models;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Customer;
import org.bson.types.ObjectId;

public class DummyOrder {
    public static Order makeDummyOrder(int i, Customer owner){
        Order order = new Order(new ObjectId());

        order.setOwner(owner);
        order.setAddress("address" + i);
        order.setCity("city" + i);
        order.setCompany("company" + i);
        order.setContactPerson("contact" + i);
        order.setCountry("country" + i);
        order.setPhoneNumber("phone" + i);
        order.setTitle("title" + i);
        order.setZipCode("zip" + i);

        return order;
    }
}
