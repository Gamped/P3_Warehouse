package dk.aau.cs.ds303e18.p3warehouse.models.restmodels;

public class RestPublisherModel {
    String companyName;
    String publisherName;

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
