package dk.aau.cs.ds303e18.p3warehouse.models.restmodels;

import dk.aau.cs.ds303e18.p3warehouse.models.users.ContactInformation;

public class RestCustomerModel extends RestUserModel {
    private ContactInformation contactInformation;

    public ContactInformation getContactInformation() {return contactInformation;}

    public void setContactInformation(ContactInformation contactInformation) {this.contactInformation = contactInformation;}
}
