package dk.aau.cs.ds303e18.p3warehouse.systemTest;

import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.users.ContactInformation;
import dk.aau.cs.ds303e18.p3warehouse.models.users.UserType;
import org.bson.types.ObjectId;

public class MakeMockClientData {
    public static Client makeClient() {
        Client client = new Client(new ObjectId());
        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setNickName("Karin");
        contactInformation.setEmail("secondclient@cc.ff");
        contactInformation.setAddress("ryvej 5");
        contactInformation.setPhoneNumber("26589542");
        contactInformation.setZipCode("6599");
        contactInformation.setCity("Bamse");
        contactInformation.setCountry("Finland");

        client.setContactInformation(contactInformation);
        client.setUserType(UserType.CLIENT);
        client.setUserName("karin");
        client.setPassword("246");

        return client;
    }

    public static Client makeSecondClient() {
        Client client = new Client(new ObjectId());
        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setNickName("Karen");
        contactInformation.setEmail("client@kare.rr");
        contactInformation.setAddress("fffavej 2");
        contactInformation.setPhoneNumber("298522654");
        contactInformation.setZipCode("9825");
        contactInformation.setCity("Hadsten");
        contactInformation.setCountry("Denmark");

        client.setContactInformation(contactInformation);
        client.setUserType(UserType.CLIENT);
        client.setUserName("karen");
        client.setPassword("123");

        return client;
    }

    public static Client makeThirdClient() {
        Client client = new Client(new ObjectId());
        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setNickName("Ryan");
        contactInformation.setEmail("publisher@fef.rr");
        contactInformation.setAddress("revej 4");
        contactInformation.setPhoneNumber("1568433546");
        contactInformation.setZipCode("5979");
        contactInformation.setCity("Rye");
        contactInformation.setCountry("Sweeden");

        client.setUserType(UserType.CLIENT);
        client.setUserName("ryan");
        client.setPassword("4546");
        client.setContactInformation(contactInformation);

        return client;
    }

    public static Client makeExtraClient() {
        Client client = new Client(new ObjectId());
        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setNickName("Hans");
        contactInformation.setEmail("fes@gr.gdr");
        contactInformation.setPhoneNumber("15334888");
        contactInformation.setAddress("møllevej 4");
        contactInformation.setZipCode("5497");
        contactInformation.setCity("Vice country");
        contactInformation.setCountry("England");

        client.setUserName("hans");
        client.setPassword("3w4");
        client.setUserType(UserType.CLIENT);
        client.setContactInformation(contactInformation);

        return client;
    }
}
