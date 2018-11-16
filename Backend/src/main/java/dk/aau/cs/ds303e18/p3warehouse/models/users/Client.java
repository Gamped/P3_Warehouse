package dk.aau.cs.ds303e18.p3warehouse.models.users;

public class Client extends User implements IClient {

    private IContactInformation contactInformation;

    public IContactInformation getContactInformation() {return contactInformation; }
}
