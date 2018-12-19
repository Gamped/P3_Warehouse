package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Publisher;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Optional;

import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockClientData.makeClient;
import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockProductData.*;
import static dk.aau.cs.ds303e18.p3warehouse.systemTest.MakeMockPublisherData.makePublisher;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ProductRepositoryTest {
    @Autowired
    ProductRepository repository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    PublisherRepository publisherRepository;

    @Before
    public void deleteAll() {
        repository.deleteAll();
        publisherRepository.deleteAll();
        clientRepository.deleteAll();
    }

    @Test
    public void findByIdTest(){
        ObjectId objectId = new ObjectId();
        Product product = new Product(objectId);
        product.setProductName("Hex Client Test2");
        System.out.println("Object ID: " + product.getHexId());
        repository.save(product);
        Optional<Product> optProduct = repository.findById(product.getId());
        Product retrievedProduct = optProduct.get();
        assertEquals(retrievedProduct.getProductName(), "Hex Client Test2");
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
        assertEquals(product.getId(), retrievedProduct.getId());
        repository.delete(product);
    }

    @Test
    public void testProductInformation() {
        Product product = makeProduct();

        repository.save(product);

        Product retrievedProduct = repository.findById(product.getId()).orElse(null);

        assertEquals(product.getQuantity(), retrievedProduct.getQuantity());
    }

    @Test
    public void testProductOwner() {
        Product product = makeProduct();
        Client client = makeClient();

        product.setOwner(client);
        client.addProduct(product);

        repository.save(product);
        clientRepository.save(client);

        Product retrievedProduct = repository.findById(product.getId()).orElse(null);

        assertEquals(client, retrievedProduct.getOwner());
    }

    @Test
    public void testProductGetOwnerRepository() {
        Product product = makeProduct();
        Publisher publisher = makePublisher();

        product.setOwner(publisher);
        publisher.addProduct(product);

        publisherRepository.save(publisher);
        repository.save(product);

        Product retrievedProduct = repository.findById(product.getId()).orElse(null);
        Publisher retrievedPublisher = publisherRepository.findById(retrievedProduct.getOwner().getHexId()).orElse(null);

        assertEquals(publisher, retrievedPublisher);
    }

    @Test
    public void testFindAllProducts() {
        Product product = makeProduct();
        Product secondProduct = makeSecondProduct();
        Product thirdProduct = makeThirdProduct();
        Product fourthProduct = makeFourthProduct();

        repository.save(product);
        repository.save(secondProduct);
        repository.save(thirdProduct);
        repository.save(fourthProduct);

        Collection<Product> productCollection  = repository.findAll();
        assertEquals(4, productCollection.size());
    }

    @Test
    public void testDeleteProductById() {
        Product product = makeProduct();

        repository.save(product);
        repository.deleteById(product.getId());

        assertNull(repository.findById(product.getId()).orElse(null));
    }

    @Test
    public void testDeleteProduct() {
        Product product = makeProduct();
        Product secondProduct = makeSecondProduct();
        Product thirdProduct = makeThirdProduct();
        Product fourthProduct = makeFourthProduct();

        repository.save(product);
        repository.save(secondProduct);
        repository.save(thirdProduct);
        repository.save(fourthProduct);

        repository.delete(product);
        Collection<Product> productCollection = repository.findAll();
        assertEquals(3, productCollection.size());
    }

    @Test
    public void deleteAllProductTest(){
        repository.deleteAll();

        assertEquals(0, repository.findAll().size());
    }
}
