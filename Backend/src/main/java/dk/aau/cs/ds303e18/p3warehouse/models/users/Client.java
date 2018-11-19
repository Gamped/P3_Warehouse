package dk.aau.cs.ds303e18.p3warehouse.models.users;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.OneToMany;
import java.util.Collection;

@Document(collection = "clients")
public class Client extends User implements Customer {


    @Id
    ObjectId id;

    private ContactInformation contactInformation;

    @OneToMany
    Publisher publisher;

    @DBRef
    Collection<Order> orders;
    @DBRef
    Collection<Product> products;

    public Client(ObjectId id){
        super(id);
        this.id = id;
    }


    public ContactInformation getContactInformation() {return contactInformation; }

    public void setContactInformation(ContactInformation contactInformation){this.contactInformation = contactInformation; }
}
