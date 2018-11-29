package dk.aau.cs.ds303e18.p3warehouse.models.users;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Stream;

public class Customer extends User {
    private ContactInformation contactInformation;
    @DBRef
    private Collection<Product> customerProducts;
    @DBRef
    private Collection<Order> customerOrders;

    private String hexId;

    Customer(ObjectId id){
        super(id);
        customerProducts = new HashSet<>();
        customerOrders = new HashSet<>();
        this.hexId = id.toHexString();
    }

    public ContactInformation getContactInformation(){
        return contactInformation;
    }

    public void setContactInformation(ContactInformation contactInformation) {
        this.contactInformation = contactInformation;
    }
    public void addProduct(Product product){
        customerProducts.add(product);
    }
    public void removeProduct(Product product){
        customerProducts.remove(product);
    }
    public Stream<Product> getProductStream(){
        return customerProducts.stream();
    }

    public void addOrder(Order order){
        customerOrders.add(order);
    }
    public void removeOrder(Order order){
        customerOrders.remove(order);
    }
    public Stream<Order> getOrderStream(){
        return customerOrders.stream();
    }

    public String getHexId(){ return this.hexId; }
}
