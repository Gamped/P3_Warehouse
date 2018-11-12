package dk.aau.cs.ds303e18.p3warehouse.warehouse;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Document(collection = "documents")
@Entity
public class Product {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private int quantity;

    public Long getId() {
        return id;
    }
    public void setId(long id){
        this.id = id;
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
}
