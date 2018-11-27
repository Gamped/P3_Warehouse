package dk.aau.cs.ds303e18.p3warehouse.models.users;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.HashSet;

@Document(collection = "publishers")
public class Publisher extends Customer {

    @Id
    private ObjectId id = new ObjectId();
    private Collection<Client> clients;
    private String publisherName;
    private String hexId;

    public Publisher(ObjectId id){
        super(id);
        this.id = id;
        this.hexId = id.toString();
        clients = new HashSet<>();
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
}


