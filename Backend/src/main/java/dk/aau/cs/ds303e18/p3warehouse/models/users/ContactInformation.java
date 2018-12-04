package dk.aau.cs.ds303e18.p3warehouse.models.users;

public class ContactInformation {
    private String email;
    private String phoneNumber;
    private String address;
    private String zipCode;
    private String nickName;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    
    @Override
    public String toString(){
        return email + " " + phoneNumber + " " + address + " " + zipCode;
    }
}
