package dk.aau.cs.ds303e18.p3warehouse.models.users;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
@Document(collection = "publishers")
public class Publisher extends User implements IPublisher {

    @Id
    private ObjectId id = new ObjectId();
    private Collection<Client> clients;
    private Collection<Order> clientOrders;
    private String companyName;
    private IContactInformation contactInformation;


    public Publisher(ObjectId id){
        super(new ObjectId());
        this.id = id;
    }

    public IContactInformation getContactInformation(){return contactInformation;}
}
