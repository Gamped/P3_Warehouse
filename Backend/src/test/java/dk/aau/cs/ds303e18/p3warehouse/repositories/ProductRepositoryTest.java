package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.users.ContactInformation;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Publisher;
import dk.aau.cs.ds303e18.p3warehouse.models.users.UserType;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

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

    @Test
    public void testFindAllProducts() {
        List<Product> products = repository.findAll();
        assertThat(products.size(), is(greaterThanOrEqualTo(0)));
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

        repository.save(product);
        clientRepository.save(client);

        Product retrievedProduct = repository.findById(product.getId()).orElse(null);

        assertEquals(client, retrievedProduct.getOwner());
    }

    @Test
    public void testProductOwnerRef() {
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
    public void deleteProductTest(){
        repository.deleteAll();

        List<Product> productList = new ArrayList<>();
        assertEquals(productList, repository.findAll());
    }
}
