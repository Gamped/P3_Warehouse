package dk.aau.cs.ds303e18.p3warehouse.models.users;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "userAuthDatabase")
public class User {

    private String userName;
    private String password;
    private ObjectId id;  // ID for the child in another collection
    private UserType userType;


    protected User(ObjectId id) {
        this.id = id;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName) {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ObjectId getId() {
        return id;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public UserType getUserType() {
        return userType;
    }

    @Override
    public String toString(){
        return userName + " " + userType;
    }
}
