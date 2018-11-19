package dk.aau.cs.ds303e18.p3warehouse.models.users;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Document(collection = "clients")
public class Client extends User implements IClient {


    @Id
    ObjectId id;
    private IContactInformation contactInformation;
    private Collection<Order> orders;

    public Client(ObjectId id){
        super(new ObjectId());
        this.id = id;
    }

    public IContactInformation getContactInformation() {return contactInformation; }
}
