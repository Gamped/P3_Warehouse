package dk.aau.cs.ds303e18.p3warehouse.models.users;

public class UserRef {
    private String userHexId;
    private UserType userType;

    public UserRef(User user){
        this.userHexId = user.getId().toHexString();
        this.userType = user.getUserType();
    }

    public String getUserHexId() {
        return userHexId;
    }

    public UserType getUserType() {
        return userType;
    }
}
