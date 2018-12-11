package dk.aau.cs.ds303e18.p3warehouse.models.restmodels;

import dk.aau.cs.ds303e18.p3warehouse.models.users.ContactInformation;

public class RestCustomerModel{

    private ContactInformation contactInformation;

    String userName;
    String password;

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(ContactInformation contactInformation) {
        this.contactInformation = contactInformation;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
