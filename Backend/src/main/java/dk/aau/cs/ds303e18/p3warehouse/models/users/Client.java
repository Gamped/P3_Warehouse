package dk.aau.cs.ds303e18.p3warehouse.models.users;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "clients")
public class Client extends User implements IClient {

    @Id
    ObjectId id;
    private IContactInformation contactInformation;

    public Client(ObjectId id){
        super(new ObjectId());
        this.id = id;
    }

    public IContactInformation getContactInformation() {return contactInformation; }
}
