package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Employee;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Publisher;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

public interface EmployeeRepository extends MongoRepository<Employee, ObjectId> {
    Employee findByNickname(@PathVariable String nickname);
    public void deleteById(@PathVariable ObjectId id);

}
