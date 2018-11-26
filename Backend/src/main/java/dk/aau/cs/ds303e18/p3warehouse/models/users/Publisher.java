package dk.aau.cs.ds303e18.p3warehouse.models.users;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
@Document(collection = "publishers")
public class Publisher extends User implements Customer {

    @Id
    private ObjectId id = new ObjectId();
    private Collection<Client> clients;
    private Collection<Order> clientOrders;
    private String publisherName;
    private ContactInformation contactInformation;
    private String hexId;

    public Publisher(ObjectId id){
        super(new ObjectId());
        this.id = id;
        this.hexId = id.toString();
    }

    public void setContactInformation(ContactInformation contactInformation) {
        this.contactInformation = contactInformation;
    }

    public String getHexId() {
        return id.toString();
    }

    public void setHexId(String hexId) {
        this.hexId = hexId;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public ContactInformation getContactInformation(){return contactInformation;}
}


