package dk.aau.cs.ds303e18.p3warehouse.models.restmodels;

import dk.aau.cs.ds303e18.p3warehouse.models.users.ContactInformation;

public class RestEmployeeModel extends RestUserModel{

    private String userName;
    private String password;
    private String nickname;
    private ContactInformation contactInformation;

    public String getUserName() {return userName;}

    public void setUserName(String userName) {this.userName = userName;}

    @Override
    public String getPassword() {return password;}

    @Override
    public void setPassword(String password) {this.password = password;}

    public String getNickname() {return nickname;}

    public void setNickname(String nickname) {this.nickname = nickname;}

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(ContactInformation contactInformation) {
        this.contactInformation = contactInformation;
    }
}
