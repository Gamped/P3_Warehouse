package dk.aau.cs.ds303e18.p3warehouse.Warehouse;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private int quantity;

    private Product(){}

    public Product(String name){
        this.name = name;
    }

    public long getId(){
        return id;
    }
    public String getName(){
        return new String(name);
    }
    public int getQuantity(){
        return quantity;
    }
}
