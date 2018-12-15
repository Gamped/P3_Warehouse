package dk.aau.cs.ds303e18.p3warehouse.models;

import dk.aau.cs.ds303e18.p3warehouse.models.users.ContactInformation;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Publisher;
import dk.aau.cs.ds303e18.p3warehouse.models.users.UserType;
import org.bson.types.ObjectId;

public class DummyPublisher {
    public static Publisher makeDummyPublisher(int i, ObjectId id){
        Publisher publisher = new Publisher(id);
        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setAddress("address " + i);
        contactInformation.setCity("city " + i);
        contactInformation.setCountry("country " + i);
        contactInformation.setEmail("mail " + i);
        contactInformation.setNickName("nickname " + i);
        contactInformation.setPhoneNumber("phone " + i);
        contactInformation.setZipCode("zip " + i);
        publisher.setContactInformation(contactInformation);
        publisher.setPassword("password" + i);
        publisher.setUserName("pUsername" + i);
        publisher.setUserType(UserType.PUBLISHER);

        return publisher;
    }
}
