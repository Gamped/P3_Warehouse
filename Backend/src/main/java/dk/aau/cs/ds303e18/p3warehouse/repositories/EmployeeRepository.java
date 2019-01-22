package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.models.users.Employee;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.PathVariable;

public interface EmployeeRepository extends MongoRepository<Employee, ObjectId> {

    Employee findByNickname(@PathVariable String nickname);
    Employee findById(@PathVariable String hexId);
    Employee findByUserName(@PathVariable String userName);
    void deleteById(@PathVariable ObjectId id);
}
