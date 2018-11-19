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

@RunWith(SpringRunner.class)
@DataMongoTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ClientRepository clientRepository;

    @Test
    public void SaveSuperclassAndExtendedClassSeparatelyTest(){
        Client client = new Client(new ObjectId());
        client.setUserType(UserType.Client);
        client.setUserName("Simon med mus");
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setEmail("Simon@MacSutterPik.xDDDDDDDD");
        client.setContactInformation(contactInformation);

        clientRepository.save(client);
        userRepository.save(client);
        User hopefullyAUser = userRepository.findAll().stream().findFirst().get();
        System.out.println(hopefullyAUser + "ELELLELELELOLOELEOELLEOELl");
    }
}
