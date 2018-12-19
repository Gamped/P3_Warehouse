package dk.aau.cs.ds303e18.p3warehouse.models.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "clients")

public class Client extends Customer {
    @Id
    ObjectId id;

    @DBRef
    Publisher publisher;

    public Client(ObjectId id) {
        super(id);
        this.id = id;
    }

    public String getHexId() {return id.toString();}

    @JsonIgnore
    public Publisher getPublisher() {return publisher;}

    @JsonProperty("publisher")
    public UserRef getPublisherRef(){return new UserRef(publisher);}

    public void setPublisher(Publisher publisher) {this.publisher = publisher;}

    @JsonIgnore
    public ObjectId getId() {return id;}

    @Override
    public String toString() {return id + " " + this.getUserName() + " " + this.getContactInformation();}
}
