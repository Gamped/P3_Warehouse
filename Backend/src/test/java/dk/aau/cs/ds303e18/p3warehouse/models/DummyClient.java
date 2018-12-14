package dk.aau.cs.ds303e18.p3warehouse.models;

import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.users.ContactInformation;
import dk.aau.cs.ds303e18.p3warehouse.models.users.UserType;
import org.bson.types.ObjectId;

public class DummyClient {
    public static Client makeDummyClient(int i){
        Client client = new Client(new ObjectId());
        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setNickName("name " + i);
        contactInformation.setEmail("mail " + i);
        contactInformation.setPhoneNumber("phone " + i);
        contactInformation.setAddress("address " + i);
        contactInformation.setZipCode("zip " + i);
        contactInformation.setCity("city " + i);

        client.setUserName("cUsername " + i);
        client.setPassword("password" + i);
        client.setUserType(UserType.CLIENT);
        client.setContactInformation(contactInformation);

        return client;
    }

    public static Client makeSpecificDummyClient(int i, ObjectId id){
        Client client = new Client(id);
        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setNickName("name " + i);
        contactInformation.setEmail("mail " + i);
        contactInformation.setPhoneNumber("phone " + i);
        contactInformation.setAddress("address " + i);
        contactInformation.setZipCode("zip " + i);
        contactInformation.setCity("city " + i);

        client.setUserName("username " + i);
        client.setPassword("password" + i);
        client.setUserType(UserType.CLIENT);
        client.setContactInformation(contactInformation);

        return client;
    }
}
