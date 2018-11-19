package dk.aau.cs.ds303e18.p3warehouse.models.warehouse;


import dk.aau.cs.ds303e18.p3warehouse.models.users.Customer;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;

@Document(collection = "products")
public class Product {
    @Id
    private ObjectId databaseId = new ObjectId();
    private String name;
    private int quantity;
    private Customer owner;
    private String productId;

    public Product(ObjectId databaseId){
        this.databaseId = databaseId;
    }

    public ObjectId getDatabaseId() {
        return databaseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Customer getOwner() {return owner; }

    public void setOwner(Customer owner) {this.owner = owner;}

    public Product withName(String name){
        this.name = name;
        return this;
    }
    public Product withQuantity(int quantity){
        this.quantity = quantity;
        return this;
    }
    @Override
    public String toString(){
        return databaseId.toString() + " " + name + " " + ((Integer)quantity).toString();
    }
}
