package dk.aau.cs.ds303e18.p3warehouse.models.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import dk.aau.cs.ds303e18.p3warehouse.CustomException.InvalidQuantityException;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Customer;
import dk.aau.cs.ds303e18.p3warehouse.models.users.UserRef;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import org.bson.types.ObjectId;
import org.omg.CORBA.DynAnyPackage.Invalid;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

@Document(collection = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId id;
    private Collection<OrderLine> orderLines;

    @DBRef private Customer owner;
    private String orderId;
    private String title;
    private String hexId;
    private Date date;
    private String address;
    private String zipCode;
    private String city;
    private String contactPerson;
    private String phoneNumber;
    private String country;
    private String company;

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Order(ObjectId id){
        this.id = id;
        this.hexId = id.toString();
        this.orderLines = new HashSet<OrderLine>();
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Collection<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(Collection<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    @JsonProperty("owner")
    public UserRef getOwnerRef(){
        return new UserRef(owner);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this. address = address;
    }


    public Order copyParametersFrom(Order order){
        this.setOrderLines(order.getOrderLines());
        this.setOrderId(order.getOrderId());
        this.setOwner(order.getOwner());
        this.setTitle(order.getTitle());
        return this;
    }

    public Order withNewOrderLine(Product product, int quantity)throws InvalidQuantityException{
        if(product.getQuantity()< quantity) {
         throw new InvalidQuantityException("Sorry, maximum quantity reached on amount");
        }
        orderLines.add(new OrderLine(product, quantity));
        return this;
    }
    public Order addProductsBackToStock(){
        for (OrderLine l : this.getOrderLines()){
            Product p = l.getProduct();
            p.setQuantity(p.getQuantity() + l.getQuantity());
        }
        return this;
    }
    public Order subtractProductsFromStock() throws InvalidQuantityException{
        for (OrderLine l : this.getOrderLines()){
            Product p = l.getProduct();
            if(l.getQuantity() > p.getQuantity()){
                throw new InvalidQuantityException("Not enough quantity of object: " + p);
            }
            else{
                p.subtract(l.getQuantity());
            }
        }
        return this;
    }

    public ObjectId getId() {
        return id;
    }

    public String getHexId() {
        return hexId;
    }

    public String toString(){
        String output = new String();
        for (OrderLine l : orderLines){
            output = output + l + " ";
        }
        return output;
    }
}