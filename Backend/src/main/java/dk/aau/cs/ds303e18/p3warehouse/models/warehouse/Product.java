package dk.aau.cs.ds303e18.p3warehouse.models.warehouse;


import com.fasterxml.jackson.annotation.JsonProperty;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Customer;
import dk.aau.cs.ds303e18.p3warehouse.models.users.UserRef;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "products")
public class Product {

    @Id
    private ObjectId id;

    private String productName;
    private String productId;
    private int quantity;
    @DBRef
    private Customer owner;

    private String hexId;


    public Product(ObjectId id){
        this.id = id;
        this.hexId = id.toString();
    }
    public Product(){}


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

    @Override
    public String toString(){
        return id.toString() + " " + productName + " " + ((Integer)quantity).toString();
    }

    public ObjectId getId() {
        return id;
    }

    public String getHexId() {return id.toString(); }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public Customer getOwner() {
        return owner;
    }

    @JsonProperty("owner")
    public UserRef getOwnerId(){
        return new UserRef(owner);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void subtract(int valueToSubtract){
        quantity = quantity - valueToSubtract;
    }
}
