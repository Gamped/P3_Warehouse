package dk.aau.cs.ds303e18.p3warehouse.models.users;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employees")
public class Employee extends User {
    @Id
    private ObjectId id;
    private String nickname;
    private String hexId;

    public Employee(ObjectId id){
        super(id);
        this.id = id;
        this.hexId = this.id.toHexString();
    }

    public String getNickname() {return nickname;}

    public void setNickname(String nickname) {this.nickname = nickname;}

    public String getHexId() {return hexId;}
}
