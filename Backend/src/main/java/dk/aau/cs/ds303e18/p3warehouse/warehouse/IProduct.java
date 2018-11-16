package dk.aau.cs.ds303e18.p3warehouse.warehouse;

public interface IProduct {
    public String getName();
    public String getId();
    public int getQuantity();
    public void setName(String name);
    public void setId(String id);
    public void setQuantity(int quantity);
}
