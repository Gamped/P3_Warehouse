package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.models.users.User;
import dk.aau.cs.ds303e18.p3warehouse.models.users.UserType;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    Optional<User> findByUserName(String userName);

    Optional<User> findByPassword(String password);

    Optional<User> findByUserType(UserType userType);

}

