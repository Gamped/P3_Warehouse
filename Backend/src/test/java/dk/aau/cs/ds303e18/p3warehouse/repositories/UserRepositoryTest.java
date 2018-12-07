package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.users.ContactInformation;
import dk.aau.cs.ds303e18.p3warehouse.models.users.User;
import dk.aau.cs.ds303e18.p3warehouse.models.users.UserType;
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

    @Before
    public void deleteAll() {
        productRepository.deleteAll();
        clientRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void SaveSuperclassAndExtendedClassSeparatelyTest(){

      //  productRepository.deleteAll();
      //  clientRepository.deleteAll();
      //  userRepository.deleteAll();

        ObjectId clientId = new ObjectId();
        Client client = new Client(clientId);
        client.setUserType(UserType.CLIENT);
        client.setUserName("Test");
        client.setPassword("123");
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setEmail("123");
        client.setContactInformation(contactInformation);



        ObjectId flyerProductId = new ObjectId();
        ObjectId noteProductId = new ObjectId();
        Product flyerProduct = new Product(flyerProductId);
        Product noteProduct = new Product(noteProductId);

        flyerProduct.setProductName("Flyers");
        noteProduct.setProductName("Notes");


        client.addProduct(flyerProduct);
        client.addProduct(noteProduct);

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
