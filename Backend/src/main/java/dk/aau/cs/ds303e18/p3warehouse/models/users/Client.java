package dk.aau.cs.ds303e18.p3warehouse.models.users;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Collection;

@Document(collection = "clients")
public class Client extends Customer {

    @Id
    ObjectId id;
  
    Publisher publisher;

    String clientName;

    public Client(ObjectId id){
        super(id);
        this.id = id;
    }


    public String getHexId() {
        return id.toString();
    }
    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public ObjectId getId() {
        return id;
    }

    @Override public String toString(){
        return id + " " + this.getUserName() + " " + this.getContactInformation();
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
