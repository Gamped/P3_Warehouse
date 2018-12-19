package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.PathVariable;

public interface ClientRepository extends MongoRepository<Client, ObjectId> {
    Iterable<Client> findByPublisherId(@PathVariable ObjectId publisherId);
    Client findById(@PathVariable String hexId);
    Client findByUserName(@PathVariable String userName);
}
