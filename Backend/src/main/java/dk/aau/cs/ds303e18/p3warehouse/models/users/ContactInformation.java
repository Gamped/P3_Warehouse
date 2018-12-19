package dk.aau.cs.ds303e18.p3warehouse.models.users;

import java.util.Objects;

public class ContactInformation {
    private String email;
    private String phoneNumber;
    private String address;
    private String zipCode;
    private String nickName;
    private String city;
    private String country;

    public String getPhoneNumber() {return phoneNumber;}

    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}

    public String getAddress() {return address;}

    public void setAddress(String address) {this.address = address;}

    public String getZipCode() {return zipCode;}

    public void setZipCode(String zipCode) {this.zipCode = zipCode;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getNickName() {return nickName;}

    public void setNickName(String nickName) {this.nickName = nickName;}

    public String getCity() {return city;}

    public String getCountry() {return country;}

    public void setCountry(String country) {this.country = country;}

    public void setCity(String city) {this.city = city;}

    @Override
    public String toString(){return email + " " + phoneNumber + " " + address + " " + zipCode;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactInformation that = (ContactInformation) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(address, that.address) &&
                Objects.equals(zipCode, that.zipCode) &&
                Objects.equals(nickName, that.nickName);
    }

    @Override
    public int hashCode() {return Objects.hash(email, phoneNumber, address, zipCode, nickName);}
}
