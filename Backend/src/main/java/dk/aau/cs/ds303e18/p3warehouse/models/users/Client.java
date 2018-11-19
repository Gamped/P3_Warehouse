package dk.aau.cs.ds303e18.p3warehouse.models.users;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Document(collection = "clients")
public class Client extends User implements Customer {

    @Id
    ObjectId id;
    private ContactInformation contactInformation;
    private Collection<Order> orders;

    public Client(ObjectId id){
        super(id);
        this.id = id;
    }

    public ContactInformation getContactInformation() {return contactInformation; }
    public void setContactInformation(ContactInformation contactInformation){this.contactInformation = contactInformation; }
}
