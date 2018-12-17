package dk.aau.cs.ds303e18.p3warehouse.models;

import dk.aau.cs.ds303e18.p3warehouse.models.users.User;
import dk.aau.cs.ds303e18.p3warehouse.models.users.UserType;
import org.bson.types.ObjectId;

public class DummyUser {
    public static User makeDummyUser(int i){
        User user = new User(new ObjectId());

        user.setUserName("username" + i);
        user.setPassword("password" + i);

        return user;
    }
}
