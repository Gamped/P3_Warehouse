package dk.aau.cs.ds303e18.p3warehouse.models.restmodels;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.OrderLine;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Customer;

import java.util.Collection;
import java.util.Date;

public class RestOrderModel {

    private Customer owner;
    private String orderId;
    private String title;
    private Date date;
    private Collection<OrderLine> orderLines;

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
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
}
