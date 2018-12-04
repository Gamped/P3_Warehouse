package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.users.ContactInformation;
import dk.aau.cs.ds303e18.p3warehouse.models.users.User;
import dk.aau.cs.ds303e18.p3warehouse.models.users.UserType;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ClientRepository clientRepository;

    @Test
    public void SaveSuperclassAndExtendedClassSeparatelyTest(){

        ObjectId clientId = new ObjectId();
        Client client = new Client(clientId);
        client.setUserType(UserType.CLIENT);
        client.setUserName("Test");
        client.setPassword("123");
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setEmail("123");
        client.setContactInformation(contactInformation);

        clientRepository.save(client);

        User user = new User(client.getId());
        user.copyFrom(client);
        userRepository.save(user);
        Optional<User> hopefullyAUser = userRepository.findById(clientId);

        Client hopefullyAClient = clientRepository.findById(client.getHexId());

        assert(hopefullyAClient.getUserName().equals(client.getUserName()));
        userRepository.delete(user);
        clientRepository.delete(client);
    }
}
