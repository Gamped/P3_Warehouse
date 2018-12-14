package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.users.ContactInformation;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Publisher;
import dk.aau.cs.ds303e18.p3warehouse.models.users.UserType;
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

    public Product makeProduct() {
        ObjectId productId = new ObjectId();

        Product product = new Product(productId);
        product.setQuantity(400);
        product.setProductName("cycling news");
        product.setProductId("342525");

        return product;
    }

    public Client makeClient() {
        ObjectId id = new ObjectId();
        Client client = new Client(id);
        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setNickName("Hans");
        contactInformation.setEmail("fes@gr.gdr");
        contactInformation.setPhoneNumber("15334888");
        contactInformation.setAddress("møllevej 4");
        contactInformation.setZipCode("5497");
        contactInformation.setCity("Aalborg");

        client.setUserName("Client");
        client.setPassword("3wdgr4");
        client.setUserType(UserType.CLIENT);
        client.setContactInformation(contactInformation);

        return client;
    }

    public Publisher makePublisher() {
        ObjectId publisherId = new ObjectId();
        Publisher publisher = new Publisher(publisherId);
        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setNickName("karen");
        contactInformation.setEmail("cyc@fff.dd");
        contactInformation.setPhoneNumber("2564866235");
        contactInformation.setAddress("mosevej 54");
        contactInformation.setZipCode("5495");
        publisher.setUserName("Publisher");
        publisher.setPassword("fee2224");
        publisher.setUserType(UserType.PUBLISHER);
        publisher.setContactInformation(contactInformation);

        return publisher;
    }

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

        assertEquals(product.getHexId(), retrievedProduct.getHexId());
        assertEquals(product.getQuantity(), retrievedProduct.getQuantity());
        assertEquals(product.getProductId(), retrievedProduct.getProductId());
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
        ObjectId id = new ObjectId();
        ObjectId productId = new ObjectId();
        ObjectId secondProductId = new ObjectId();

        Product secondProduct = new Product(id);
        secondProduct.setProductName("ship");
        secondProduct.setQuantity(65);
        secondProduct.setProductId("3243354654");

        Product thirdProduct = new Product(secondProductId);
        thirdProduct.setProductName("bus");
        thirdProduct.setQuantity(66);
        thirdProduct.setProductId("3r23543645765");

        Product fourthProduct = new Product(productId);
        fourthProduct.setProductName("music");
        fourthProduct.setQuantity(26);
        fourthProduct.setProductId("35264564765765");

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
        ObjectId id = new ObjectId();
        ObjectId productId = new ObjectId();
        ObjectId secondProductId = new ObjectId();

        Product secondProduct = new Product(id);
        secondProduct.setProductName("ship");
        secondProduct.setQuantity(65);
        secondProduct.setProductId("3243354654");

        Product thirdProduct = new Product(secondProductId);
        thirdProduct.setProductName("bus");
        thirdProduct.setQuantity(66);
        thirdProduct.setProductId("3r23543645765");

        Product fourthProduct = new Product(productId);
        fourthProduct.setProductName("music");
        fourthProduct.setQuantity(26);
        fourthProduct.setProductId("35264564765765");

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
