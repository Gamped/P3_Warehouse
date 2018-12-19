package dk.aau.cs.ds303e18.p3warehouse.models.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestOrderModel;
import dk.aau.cs.ds303e18.p3warehouse.exceptions.InvalidQuantityException;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Customer;
import dk.aau.cs.ds303e18.p3warehouse.models.users.UserRef;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;

@Document(collection = "orders")
public class Order extends RestOrderModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId id;
    @DBRef private Customer owner;
    private String hexId;
    private Date date;

    public Order(ObjectId id){

        this.id = id;
        this.hexId = id.toString();
        this.date = new Date();
    }


    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}

    public Customer getOwner() {return owner;}

    public void setOwner(Customer owner) {this.owner = owner;}

    @JsonProperty("owner")
    public UserRef getOwnerRef(){return new UserRef(owner);}

    public Order copyParametersFrom(Order order){

        this.setOrderLines(order.getOrderLines());
        this.setOrderId(order.getOrderId());
        this.setOwner(order.getOwner());
        this.setTitle(order.getTitle());
        return this;
    }

    public Order withNewOrderLine(Product product, int quantity)throws InvalidQuantityException{

        if(product.getQuantity()< quantity) {
            throw new InvalidQuantityException("Sorry, maximum quantity reached on amount");
        }
        getOrderLines().add(new OrderLine(product, quantity));
        return this;
    }

    public Order addProductsBackToStock(){

        for (OrderLine l : this.getOrderLines()){
            Product p = l.getProduct();
            p.setQuantity(p.getQuantity() + l.getQuantity());
        }
        return this;
    }

    public Order subtractProductsFromStock() throws InvalidQuantityException{

        for (OrderLine l : this.getOrderLines()){
            Product p = l.getProduct();
            if(l.getQuantity() > p.getQuantity()){
                throw new InvalidQuantityException("Not enough quantity of object: " + p);
            } else {
                p.subtract(l.getQuantity());
            }
        }
        return this;
    }

    public ObjectId getId() {return id;}

    public String getHexId() {return hexId;}

    public String toString(){

        String output = new String();
        for (OrderLine l : getOrderLines()){
            output = output + l + " ";
        }
        return output;
    }
}