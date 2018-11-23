package dk.aau.cs.ds303e18.p3warehouse.models.orders;

import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orderlines")
public class OrderLine {
    @Id
    private ObjectId id;
    @DBRef
    private Order order;
    private Product product;
    private int quantity;

    public OrderLine(ObjectId id){
        this.id = id;
    }
}

