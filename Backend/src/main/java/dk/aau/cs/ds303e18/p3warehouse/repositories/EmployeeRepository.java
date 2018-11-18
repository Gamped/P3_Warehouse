package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.models.users.IEmployee;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<IEmployee, ObjectId> {
}
