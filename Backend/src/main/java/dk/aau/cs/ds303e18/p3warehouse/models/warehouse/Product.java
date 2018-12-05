package dk.aau.cs.ds303e18.p3warehouse.models.warehouse;


import com.fasterxml.jackson.annotation.JsonIgnore;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Customer;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import java.util.Objects;

@Document(collection = "products")
public class Product {

    @Id
    private ObjectId id;

    private String productName;
    private String productId;
    private int quantity;

    private String hexId;
    private DBRef owner;


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


    public DBRef getOwner() {return owner; }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    @Override
    public String toString(){
        return id.toString() + " " + productName + " " + ((Integer)quantity).toString();
    }

    public ObjectId getId() {
        return id;
    }

    public String getHexId() {return id.toString(); }

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
}
