package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ProductRepositoryTest {
    @Autowired
    ProductRepository repository;

    @Test
    public void findByIdTest(){
        Product product = new Product(new ObjectId());
        product.setName("Red Alert");
        product.setQuantity(100);
        repository.save(product);
        Product foundProduct = repository.findByDatabaseId(product.getDatabaseId());
        Assert.assertEquals(product.getDatabaseId(), foundProduct.getDatabaseId());
    }
}
