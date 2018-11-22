package dk.aau.cs.ds303e18.p3warehouse.models.orders;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
public class Order {
    @Id
    String id;

    public Order(){
    }

    public String getId(){
        return id;
    }
}
