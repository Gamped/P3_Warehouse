package dk.aau.cs.ds303e18.p3warehouse.models.restmodels;

public class RestProductModel {

    private String productName;
    private String productId;
    private int quantity;

    public String getProductName() {return productName;}

    public String setProductName(String productName) {return this.productName = productName;}

    public int getQuantity() {return quantity;}

    public void setQuantity(int quantity) {this.quantity = quantity;}

    public String getProductId() {return productId;}

    public void setProductId(String productId) {this.productId = productId;}

    public String modelToString() {
        return "RestProductModel: " + this.getProductName() + " " + this.getProductId() + " " + this.getQuantity();
    }
}
