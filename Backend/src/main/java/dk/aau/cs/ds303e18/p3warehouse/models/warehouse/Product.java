package dk.aau.cs.ds303e18.p3warehouse.models.warehouse;


import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Customer;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;

@Document(collection = "clients")
public class Product {

    @Id
    private ObjectId id;

    private String productName;
    private String productId;
    private int quantity;

    private String hexId;

    @DBRef
    private Customer owner;


    public Product(ObjectId id){
        this.id = id;
        this.hexId = id.toString();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductId(String productId){this.productId = productId; }
    public String getProductId(){return productId; }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public Customer getOwner() {return owner; }

    public void setOwner(Customer owner) {this.owner = owner;}

    @Override
    public String toString(){
        return id.toString() + " " + productName + " " + ((Integer)quantity).toString();
    }

    public ObjectId getId() {
        return id;
    }

    public String getHexId() {return id.toString(); }

}
