package dk.aau.cs.ds303e18.p3warehouse.models.restmodels;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.OrderLine;
import java.util.ArrayList;


public class RestOrderModel {
    // private JSONArray orderLines;
    private ArrayList<OrderLine> orderLines;
    private int orderId;
    private String title;
    private String address;
    private String zipCode;
    private String city;
    private String contactPerson;
    private String phoneNumber;
    private String country;
    private String company;

    public RestOrderModel() {this.orderLines = new ArrayList<>();}

    public ArrayList<OrderLine> getOrderLines() {return orderLines;}

    public void setOrderLines(ArrayList<OrderLine> orderLines) {this.orderLines = orderLines;}

    public int getOrderId() {return orderId;}

    public void setOrderId(int orderId) {this.orderId = orderId;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getAddress() {return address;}

    public void setAddress(String address) {this.address = address;}

    public String getZipCode() {return zipCode;}

    public void setZipCode(String zipCode) {this.zipCode = zipCode;}

    public String getCity() {return city;}

    public void setCity(String city) {this.city = city;}

    public String getContactPerson() {return contactPerson;}

    public void setContactPerson(String contactPerson) {this.contactPerson = contactPerson;}

    public String getPhoneNumber() {return phoneNumber;}

    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}

    public String getCountry() {return country;}

    public void setCountry(String country) {this.country = country;}

    public String getCompany() {return company;}

    public void setCompany(String company) {this.company = company;}
}
