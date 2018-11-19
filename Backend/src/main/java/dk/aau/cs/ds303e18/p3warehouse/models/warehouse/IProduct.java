package dk.aau.cs.ds303e18.p3warehouse.models.warehouse;

import org.bson.types.ObjectId;

public interface IProduct {
    public String getName();
    public ObjectId getDatabaseId();
    public int getQuantity();
    public void setName(String name);
    public void setQuantity(int quantity);
    public String toString();
}
