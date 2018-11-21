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
        client.setUserType(UserType.Client);
        client.setUserName("Simon med mus");
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setEmail("Simon@MacSutterPik.xDDDDDDDD");
        client.setContactInformation(contactInformation);

        clientRepository.save(client);
        userRepository.save(client);
        User hopefullyAUser = userRepository.findAll().get(0);
        Optional<Client> hopefullyAClient = clientRepository.findById(hopefullyAUser.getId());
        System.out.println(hopefullyAUser.getUserName());
        System.out.println(hopefullyAClient.get().getUserName());
        System.out.println();
        System.out.println(hopefullyAClient.get().getContactInformation().getEmail());
        System.out.println();
        System.out.println(hopefullyAUser.getUserType());
        System.out.println(hopefullyAClient.get().getUserType());

        for(User u : userRepository.findAll()){
            System.out.println(u);
        }
        assert(false);
    }
}
