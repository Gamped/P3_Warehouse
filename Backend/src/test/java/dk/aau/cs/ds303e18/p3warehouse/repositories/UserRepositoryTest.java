package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.models.users.*;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    PublisherRepository publisherRepository;
    @Autowired
    OrderRepository orderRepository;

    @Before
    public void deleteAll() {
        productRepository.deleteAll();
        clientRepository.deleteAll();
        userRepository.deleteAll();
        orderRepository.deleteAll();
    }

    @Test
    public void SaveSuperclassAndExtendedClassSeparatelyTest(){

        productRepository.deleteAll();
        clientRepository.deleteAll();
        userRepository.deleteAll();
            publisherRepository.deleteAll();

        ObjectId clientId = new ObjectId();
        ObjectId publisherId = new ObjectId();
        Client client = new Client(clientId);
        client.setUserType(UserType.CLIENT);
        client.setUserName("Test");
        client.setPassword("123");
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setEmail("123");
        client.setContactInformation(contactInformation);

        Publisher publisher = new Publisher(publisherId);
        publisher.setUserType(UserType.PUBLISHER);
        ContactInformation contactInformationPub = new ContactInformation();
        contactInformation.setNickName("Publisher");
        publisher.setContactInformation(contactInformationPub);
        publisher.setUserName("Publisher");
        publisher.setPassword("123");

        publisher.addClient(client);

        ObjectId flyerProductId = new ObjectId();
        ObjectId noteProductId = new ObjectId();
        ObjectId computerProductId = new ObjectId();
        Product computerProduct = new Product(computerProductId);
        Product flyerProduct = new Product(flyerProductId);
        Product noteProduct = new Product(noteProductId);


        flyerProduct.setProductName("Flyers");
        noteProduct.setProductName("Notes");
        flyerProduct.setQuantity(123);
        noteProduct.setQuantity(755);
        computerProduct.setProductName("Computer");
        computerProduct.setQuantity(244);

        publisher.addProduct(computerProduct);
        computerProduct.setOwner(publisher);

        client.addProduct(flyerProduct);
        client.addProduct(noteProduct);
        flyerProduct.setOwner(client);
        noteProduct.setOwner(client);

        User user = new User(client.getId());
        user.copyFrom(client);
        userRepository.save(user);
        clientRepository.save(client);
        productRepository.save(noteProduct);
        productRepository.save(flyerProduct);

        Client queriedClient = clientRepository.findById(client.getId()).orElse(null);
        System.out.println(queriedClient.getProductStream());

/*
        User user = new User(client.getId());
        user.copyFrom(client);
        userRepository.save(user);
        Optional<User> hopefullyAUser = userRepository.findById(clientId);

        Client hopefullyAClient = clientRepository.findById(client.getHexId());

        assert(hopefullyAClient.getUserName().equals(client.getUserName()));
       // userRepository.delete(user);
       // clientRepository.delete(client);
   */
    }

}
