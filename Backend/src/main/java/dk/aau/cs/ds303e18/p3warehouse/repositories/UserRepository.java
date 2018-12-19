package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.models.users.User;
import dk.aau.cs.ds303e18.p3warehouse.models.users.UserType;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    Optional<User> findByUserName(String userName);

    Optional<User> findByPassword(String password);

    Optional<User> findByUserType(UserType userType);

    Optional<User> findById(ObjectId id);
}

