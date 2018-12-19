package dk.aau.cs.ds303e18.p3warehouse.models.users;

public class UserRef {
    private String userHexId;
    private UserType userType;
    private String nickName;

    public UserRef(Customer customer){

        if(customer == null){
            this.userHexId = null;
            this.userType = null;
            this.nickName = null;
        } else {
            this.userHexId = customer.getId().toHexString();
            this.userType = customer.getUserType();
            this.nickName = customer.getContactInformation().getNickName();
        }
    }

    public String getUserHexId() {return userHexId;}

    public UserType getUserType() {return userType;}

    public String getNickName() {return nickName;}
}
