package dk.aau.cs.ds303e18.p3warehouse.models.warehouse;


import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Customer;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;

@Document(collection = "products")
public class Product {
    @Id
    private ObjectId id;

    private String hexId;
    private String name;
    private int quantity;
    private String productId;

    @DBRef
    private Customer owner;


    public Product(ObjectId id){
        this.id = id;
        this.hexId = id.toString();
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
    public void setProductId(String productId){this.productId = productId; }
    public String getProductId(){return productId; }

    public Product copyParametersFrom(Product product){
        this.setName(product.getName());
        this.setQuantity(product.getQuantity());
        this.setOwner(product.getOwner());
        this.setProductId(product.getProductId());
        return this;
    }
    @Override
    public String toString(){
        return id.toString() + " " + name + " " + ((Integer)quantity).toString();
    }

    public ObjectId getId() {
        return id;
    }

    public String getHexId() {return id.toString(); }

}
