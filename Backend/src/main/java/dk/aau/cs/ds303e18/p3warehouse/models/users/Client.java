package dk.aau.cs.ds303e18.p3warehouse.models.users;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Collection;

@Document(collection = "clients")
public class Client extends User implements Customer {

    @Id
    ObjectId id;

    private ContactInformation contactInformation;
  
    Publisher publisher;

    Collection<Order> orders;
    Collection<Product> products;

    public Client(ObjectId id){
        super(id);
        this.id = id;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Collection<Order> getOrders() {
        return orders;
    }

    public void setOrders(Collection<Order> orders) {
        this.orders = orders;
    }

    public Collection<Product> getProducts() {
        return products;
    }

    public void setProducts(Collection<Product> products) {
        this.products = products;
    }

    public ObjectId getId() {
        return id;
    }

    public ContactInformation getContactInformation() {return contactInformation; }

    public void setContactInformation(ContactInformation contactInformation){this.contactInformation = contactInformation; }

    @Override public String toString(){
        return id + " " + getUserName() + " " + contactInformation;
    }
}
