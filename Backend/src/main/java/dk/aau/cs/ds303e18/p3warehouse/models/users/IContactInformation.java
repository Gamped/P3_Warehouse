package dk.aau.cs.ds303e18.p3warehouse.models.users;

public interface IContactInformation {
    public String getEmail();
    public void setEmail(String email);
    public String getAddress();
    public void setAddress(String address);
    public String getPhoneNumber();
    public void setPhoneNumber(String phoneNumber);
    public String getZipCode();
    public void setZipCode(String zipCode);
}
