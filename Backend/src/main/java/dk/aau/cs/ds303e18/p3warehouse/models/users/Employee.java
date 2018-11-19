package dk.aau.cs.ds303e18.p3warehouse.models.users;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employees")
public class Employee extends User implements IEmployee {

    @Id
    private ObjectId id;
    private String nickname;

    public Employee(ObjectId id){
        super(new ObjectId());
        this.id = id;
    }
}
