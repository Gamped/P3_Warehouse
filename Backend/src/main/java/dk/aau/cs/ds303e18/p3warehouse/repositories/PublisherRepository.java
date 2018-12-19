package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.models.users.Publisher;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface PublisherRepository extends MongoRepository<Publisher, ObjectId> {

    List<Publisher> findAll();

    Optional<Publisher> findById(@PathVariable String hexId);

    Publisher findByUserName(@PathVariable String userName);

    void deleteById(String hexId);

    Publisher save(Publisher publisher);
}
