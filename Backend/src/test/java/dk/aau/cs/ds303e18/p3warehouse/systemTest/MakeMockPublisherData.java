package dk.aau.cs.ds303e18.p3warehouse.systemTest;

import dk.aau.cs.ds303e18.p3warehouse.models.users.ContactInformation;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Publisher;
import dk.aau.cs.ds303e18.p3warehouse.models.users.UserType;
import org.bson.types.ObjectId;

public class MakeMockPublisherData {

    public static Publisher makePublisher() {

        Publisher publisher = new Publisher(new ObjectId());
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setNickName("Monste");
        contactInformation.setEmail("publisher@cc.ff");
        contactInformation.setAddress("luevej 4");
        contactInformation.setPhoneNumber("29575236");
        contactInformation.setZipCode("1694");
        contactInformation.setCity("Århus");
        contactInformation.setCountry("Denmark");

        publisher.setContactInformation(contactInformation);
        publisher.setUserType(UserType.PUBLISHER);
        publisher.setUserName("monste");
        publisher.setPassword("123");

        return publisher;
    }

    public static Publisher makeSecondPublisher() {

        Publisher publisher = new Publisher(new ObjectId());
        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setNickName("lej og byg");
        contactInformation.setEmail("secondPublisher@ff.cc");
        contactInformation.setPhoneNumber("26546235");
        contactInformation.setAddress("hale 34");
        contactInformation.setZipCode("2695");
        contactInformation.setCity("Hals");
        contactInformation.setCountry("Denmark");

        publisher.setUserType(UserType.PUBLISHER);
        publisher.setUserName("hals");
        publisher.setPassword("544");
        publisher.setContactInformation(contactInformation);

        return publisher;
    }

    public static Publisher makeThirdPublisher() {

        Publisher publisher = new Publisher(new ObjectId());
        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setNickName("music store");
        contactInformation.setEmail("thirdPublisher@ff.cc");
        contactInformation.setPhoneNumber("87525632");
        contactInformation.setAddress("gyldenvej 4");
        contactInformation.setZipCode("2796");
        contactInformation.setCity("Padborg");
        contactInformation.setCountry("Norway");

        publisher.setUserType(UserType.PUBLISHER);
        publisher.setUserName("music");
        publisher.setPassword("433");
        publisher.setContactInformation(contactInformation);

        return publisher;
    }
}
