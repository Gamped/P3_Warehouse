package dk.aau.cs.ds303e18.p3warehouse.systemTest;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import org.bson.types.ObjectId;

import java.util.Date;

public class MakeMockOrderData {
    public static Order makeOrder() {
        Order order = new Order(new ObjectId());

        order.setTitle("sports");
        order.setDate(new Date());
        order.setAddress("mour 4");
        order.setOrderId("35223645654ddd");
        order.setCity("Serene");
        order.setPhoneNumber("66498726");
        order.setZipCode("5979");
        order.setCountry("Denmark");
        order.setCompany("sports shop");
        order.setContactPerson("Molly");

        return order;
    }

    public static Order makeSecondOrder() {
        Order order = new Order(new ObjectId());

        order.setTitle("music");
        order.setDate(new Date());
        order.setAddress("foul 23");
        order.setOrderId("242543643678");
        order.setCity("Moonlight");
        order.setPhoneNumber("56846987");
        order.setZipCode("9985");
        order.setCountry("Denmark");
        order.setCompany("music store");
        order.setContactPerson("ole");

        return order;
    }

    public static Order makeThirdOrder() {
        Order order = new Order(new ObjectId());

        order.setTitle("books");
        order.setDate(new Date());
        order.setAddress("doo 4");
        order.setOrderId("325346436");
        order.setCity("Stone");
        order.setPhoneNumber("43599856");
        order.setZipCode("5955");
        order.setCountry("Denmark");
        order.setCompany("book store");
        order.setContactPerson("Hans");

        return order;
    }

    public static Order makeFourthOrder() {
        Order order = new Order(new ObjectId());

        order.setTitle("museum advertisement");
        order.setDate(new Date());
        order.setAddress("yellow 2");
        order.setOrderId("3254368888");
        order.setCity("Gesser");
        order.setPhoneNumber("46488798");
        order.setZipCode("1354");
        order.setCountry("Denmark");
        order.setCompany("Museum");
        order.setContactPerson("Mogens");

        return order;
    }

    public static Order makeFifthOrder() {
        Order order = new Order(new ObjectId());

        order.setTitle("magazine");
        order.setDate(new Date());
        order.setAddress("moon 90");
        order.setOrderId("325378897430");
        order.setCity("Smalling");
        order.setPhoneNumber("15988526");
        order.setZipCode("9335");
        order.setCountry("Sweeden");
        order.setCompany("car magazine maker");
        order.setContactPerson("Maren");

        return order;
    }

    public static Order makeSixthOrder() {
        Order order = new Order(new ObjectId());

        order.setTitle("discount");
        order.setDate(new Date());
        order.setAddress("sun 33");
        order.setOrderId("3536347568");
        order.setCity("Kunsten");
        order.setPhoneNumber("26885487");
        order.setZipCode("9526");
        order.setCountry("Denmark");
        order.setCompany("general store");
        order.setContactPerson("Mark");

        return order;
    }

    public static Order makeExtraOrder() {
        Order order = new Order(new ObjectId());

        order.setTitle("flyers");
        order.setOrderId("3255");
        order.setAddress("musvej 3");
        order.setDate(new Date());
        order.setCity("Aalborg");
        order.setPhoneNumber("99635485");
        order.setZipCode("9523");
        order.setCountry("Denmark");
        order.setCompany("News company");
        order.setContactPerson("Oliver");

        return order;
    }
}
