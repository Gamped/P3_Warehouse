package dk.aau.cs.ds303e18.p3warehouse.models.users;

import org.bson.types.ObjectId;

public class Customer extends User {
    private ContactInformation contactInformation;

    private String hexId;

    Customer(ObjectId id){
        super(id);
        this.hexId = id.toHexString();
    }

    public ContactInformation getContactInformation(){
        return contactInformation;
    }

    public void setContactInformation(ContactInformation contactInformation){
        this. contactInformation = contactInformation;
    }

    public String getHexId() {
        return hexId;
    }
}
