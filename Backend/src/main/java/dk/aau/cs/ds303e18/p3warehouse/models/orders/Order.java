package dk.aau.cs.ds303e18.p3warehouse.models.orders;

import dk.aau.cs.ds303e18.p3warehouse.models.users.Customer;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Collection;
import java.util.HashSet;

@Document(collection = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId id;
    private Collection<OrderLine> orderLines;
    @DBRef
    private Customer owner;
    private String orderId;
    private String title;
    private String hexid;

    public Order(ObjectId id){
        this.id = id;
        this.hexid = id.toString();
        this.orderLines = new HashSet<OrderLine>();
    }


    public Collection<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(Collection<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Order copyParametersFrom(Order order){
        this.setOrderLines(order.getOrderLines());
        this.setOrderId(order.getOrderId());
        this.setOwner(order.getOwner());
        this.setTitle(order.getTitle());
        return this;
    }

    public Order withNewOrderLine(Product product, int quantity){
        //TODO: lav exception-checking på at quantity ikke er over produkt.quantity
        orderLines.add(new OrderLine(product, quantity));
        return this;
    }

    public ObjectId getId() {
        return id;
    }

    public String getHexid() {
        return hexid;
    }
}
