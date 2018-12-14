package dk.aau.cs.ds303e18.p3warehouse.models;

import dk.aau.cs.ds303e18.p3warehouse.models.users.Customer;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import org.bson.types.ObjectId;

public class DummyProduct {
    public static Product makeDummyProduct(int i, Customer owner){
        Product product = new Product(new ObjectId());

        product.setOwner(owner);
        product.setProductId("id " + i);
        product.setProductName("name " + i);
        product.setQuantity(i);

        return product;
    }
}
