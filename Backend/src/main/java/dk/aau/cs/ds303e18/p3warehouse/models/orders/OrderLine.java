package dk.aau.cs.ds303e18.p3warehouse.models.orders;

import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Objects;

public class OrderLine {
    @DBRef
    private Product product;
    private int quantity;
    private String productHexId;

    public OrderLine(Product product, int quantity){
        this.product = product;
        this.quantity = quantity;
        this.productHexId = product.getHexId();

    }

    public OrderLine(){}


    public String getProductHexId() {
        return productHexId;
    }

    public void setProductHexId(String productHexId) {
        this.productHexId = productHexId;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String toString(){
        return product + " " + quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderLine orderLine = (OrderLine) o;
        return quantity == orderLine.quantity &&
                Objects.equals(product, orderLine.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity);
    }
}

