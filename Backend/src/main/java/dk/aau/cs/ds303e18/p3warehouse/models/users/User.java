package dk.aau.cs.ds303e18.p3warehouse.models.users;

public class User {
    private String userName;
    private String password;
    // ID for the child in another collection
    private UserType userType;


    public User() {

    }

    public getUserName(){
        return userName;
    }

    public setUserName(String userName) {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public UserType getUserType() {
        return userType;
    }
}
