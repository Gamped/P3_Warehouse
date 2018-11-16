package dk.aau.cs.ds303e18.p3warehouse.warehouse;

public interface IProduct {
    public String getName();
    public String getDatabaseId();
    public int getQuantity();
    public void setName(String name);
    public void setDatabaseId(String databaseId);
    public void setQuantity(int quantity);
}
