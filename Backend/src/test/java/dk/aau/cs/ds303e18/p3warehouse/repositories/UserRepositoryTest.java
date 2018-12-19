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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
    @Autowired
    EmployeeRepository employeeRepository;

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

        flyerProduct.setOwner(client);
        noteProduct.setOwner(client);
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
    }

    public User makeUser() {

        ObjectId id = new ObjectId();
        User user = new User(id);
        user.setUserType(UserType.PUBLISHER);
        user.setUserName("user");
        user.setPassword("214235245");

        return user;
    }

    @Test
    public void testUserFindById() {

        ObjectId id = new ObjectId();
        User user = new User(id);

        userRepository.save(user);

        User retrievedUser = userRepository.findById(id).orElse(null);

        assertEquals(id, retrievedUser.getId());
    }

    @Test
    public void testUserFindInformation() {

        User user = makeUser();

        userRepository.save(user);

        User retrievedUser = userRepository.findById(user.getId()).orElse(null);

        assertEquals(user.getUserType(), retrievedUser.getUserType());
    }

    @Test
    public void testFindBy() {

        ObjectId secondId = new ObjectId();
        Client thirdClient = new Client(secondId);
        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setNickName("Karen");
        contactInformation.setEmail("client@kare.rr");
        contactInformation.setAddress("fffavej 2");
        contactInformation.setPhoneNumber("298522654");
        contactInformation.setZipCode("9825");
        contactInformation.setCity("Hadsten");

        thirdClient.setContactInformation(contactInformation);
        thirdClient.setUserType(UserType.CLIENT);
        thirdClient.setUserName("secondclient");
        thirdClient.setPassword("esfesgrs");

        User user = new User(thirdClient.getId());
        user.copyFrom(thirdClient);

        clientRepository.save(thirdClient);
        userRepository.save(user);

        User retrievedUser = userRepository.findById(user.getId()).orElse(null);
        User secondRetrievedUser = userRepository.findById(thirdClient.getId()).orElse(null);

        assertEquals(retrievedUser, secondRetrievedUser);

    }

    @Test
    public void testUserCopyFrom() {

        ObjectId id = new ObjectId();
        Client client = new Client(id);
        client.setPassword("rw435r");
        client.setUserName("client");
        client.setUserType(UserType.CLIENT);
        User user = new User(client.getId());
        user.copyFrom(client);

        assertEquals(client.getUserName(), user.getUserName());
    }

    @Test
    public void testFindUserByPublisherId() {

        ObjectId id = new ObjectId();
        Publisher publisher = new Publisher(id);
        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setNickName("music store");
        contactInformation.setEmail("thirdPublisher@ff.cc");
        contactInformation.setPhoneNumber("87525632");
        contactInformation.setAddress("gyldenvej 4");
        contactInformation.setZipCode("2796");
        contactInformation.setCity("Padborg");

        publisher.setUserType(UserType.PUBLISHER);
        publisher.setUserName("thirdpublisher");
        publisher.setPassword("r43tdhytf");
        publisher.setContactInformation(contactInformation);

        User user = new User(publisher.getId());
        user.copyFrom(publisher);

        publisherRepository.save(publisher);
        userRepository.save(user);

        User retrievedUser = userRepository.findById(publisher.getId()).orElse(null);
        assertEquals(publisher.getUserName(), retrievedUser.getUserName());
    }

    @Test
    public void testFindUser() {

        ObjectId id = new ObjectId();
        User user = new User(id);
        Publisher publisher = new Publisher(user.getId());

        userRepository.save(user);
        publisherRepository.save(publisher);

        User retrievedUser = userRepository.findById(user.getId()).orElse(null);
        Publisher retrievedPublisher = publisherRepository.findById(retrievedUser.getId()).orElse(null);

        assertEquals(publisher.getId(), retrievedPublisher.getId());
    }

    @Test
    public void testFindByUserName() {

        Employee emp = new Employee(new ObjectId());
        emp.setNickname("Jane");
        emp.setUserType(UserType.EMPLOYEE);
        emp.setPassword("123");
        emp.setUserName("jane");

        User user = new User(emp.getId());
        user.copyFrom(emp);

        userRepository.save(user);
        employeeRepository.save(emp);

        User retrievedUser = userRepository.findByUserName(emp.getUserName()).orElse(null);
        assertEquals(emp.getUserName(), retrievedUser.getUserName());
    }

    @Test
    public void testFindUserByPassWord() {

        ObjectId id = new ObjectId();
        Publisher publisher = new Publisher(id);
        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setNickName("music store");
        contactInformation.setEmail("thirdPublisher@ff.cc");
        contactInformation.setPhoneNumber("87525632");
        contactInformation.setAddress("gyldenvej 4");
        contactInformation.setZipCode("2796");
        contactInformation.setCity("Padborg");

        publisher.setUserType(UserType.PUBLISHER);
        publisher.setUserName("thirdpublisher");
        publisher.setPassword("r43tdhytf");
        publisher.setContactInformation(contactInformation);

        User user = new User(publisher.getId());
        user.copyFrom(publisher);

        publisherRepository.save(publisher);
        userRepository.save(user);

        User retrievedUser = userRepository.findByPassword(publisher.getPassword()).orElse(null);
        assertEquals(publisher.getId(), retrievedUser.getId());
    }

    @Test
    public void testFindUserByUserType() {

        ObjectId secondId = new ObjectId();
        Client thirdClient = new Client(secondId);
        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setNickName("Karen");
        contactInformation.setEmail("client@kare.rr");
        contactInformation.setAddress("fffavej 2");
        contactInformation.setPhoneNumber("298522654");
        contactInformation.setZipCode("9825");
        contactInformation.setCity("Hadsten");

        thirdClient.setContactInformation(contactInformation);
        thirdClient.setUserType(UserType.CLIENT);
        thirdClient.setUserName("secondclient");
        thirdClient.setPassword("esfesgrs");

        User user = new User(thirdClient.getId());
        user.copyFrom(thirdClient);

        userRepository.save(user);
        clientRepository.save(thirdClient);

        User retrievedUsesr = userRepository.findByUserType(thirdClient.getUserType()).orElse(null);
        assertEquals(thirdClient.getId(), retrievedUsesr.getId());
    }

    @Test
    public void deleteUserById() {

        ObjectId id = new ObjectId();
        Publisher publisher = new Publisher(id);
        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setNickName("music store");
        contactInformation.setEmail("thirdPublisher@ff.cc");
        contactInformation.setPhoneNumber("87525632");
        contactInformation.setAddress("gyldenvej 4");
        contactInformation.setZipCode("2796");
        contactInformation.setCity("Padborg");

        publisher.setUserType(UserType.PUBLISHER);
        publisher.setUserName("thirdpublisher");
        publisher.setPassword("r43tdhytf");
        publisher.setContactInformation(contactInformation);

        User user = new User(publisher.getId());
        user.copyFrom(publisher);

        publisherRepository.save(publisher);
        userRepository.save(user);

        userRepository.deleteById(user.getId());

        assertNull(userRepository.findById(publisher.getId()).orElse(null));
    }

    @Test
    public void testDeleteAllUsers() {

        userRepository.deleteAll();
        assertEquals(0, userRepository.findAll().size());
    }
}
