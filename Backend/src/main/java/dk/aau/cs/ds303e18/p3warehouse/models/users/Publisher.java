package dk.aau.cs.ds303e18.p3warehouse.models.users;

import java.util.Collection;

public class Publisher extends User implements IPublisher {
    private String id;
    private Collection<Client> clients;
    private String companyName;
    private IContactInformation contactInformation;

    public IContactInformation getContactInformation(){return contactInformation;}
}
