package dk.aau.cs.ds303e18.p3warehouse.models.users;

import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "userAuthDatabase")
public class User {

    private String userName;
    private String password;
    private String nickName;
    private ObjectId id;  // ID for the child in another collection
    private UserType userType;

    protected User(ObjectId id) {
        this.id = id;
    }

    public User(User userToCopyFrom){
        BeanUtils.copyProperties(userToCopyFrom, this);
    }
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                userType == user.userType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userType);
    }
}
