package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ProductRepositoryTest {
    @Autowired
    ProductRepository repository;

    @Test
    public void findByIdTest(){
        ObjectId objectId = new ObjectId();
        Product product = new Product(objectId);
        product.setProductName("Hex Client Test2");
        System.out.println("Object ID: " + product.getHexId());
        repository.save(product);
        Optional<Product> optProduct = repository.findById(product.getId());
        Product retrievedProduct = optProduct.get();
        Assert.assertEquals(retrievedProduct.getProductName(), "Hex Client Test2");
        repository.delete(product);
    }

    @Test
    public void saveTest(){
        ObjectId id = new ObjectId();
        Product product = new Product(id);
        product.setProductName("Red Alert");
        product.setQuantity(100);
        repository.save(product);
        Optional<Product> optProduct = repository.findById(product.getId());
        Product retrievedProduct = optProduct.get();
        Assert.assertEquals(product.getId(), retrievedProduct.getId());
        repository.delete(product);
    }
}
