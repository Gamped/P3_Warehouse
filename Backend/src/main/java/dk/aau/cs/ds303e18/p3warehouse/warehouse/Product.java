package dk.aau.cs.ds303e18.p3warehouse.warehouse;


import dk.aau.cs.ds303e18.p3warehouse.models.users.Customer;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Document(collection = "products")
public class Product implements IProduct {
    @Id @GeneratedValue
    private String databaseId;
    private String name;
    private int quantity;
    private Customer owner;
    private String productId;

    public String getDatabaseId() {
        return databaseId;
    }
    public void setDatabaseId(String databaseId){
        this.databaseId = databaseId;
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
    public String toString(){
        return databaseId.toString() + " " + name + " " + ((Integer)quantity).toString();
    }
}
