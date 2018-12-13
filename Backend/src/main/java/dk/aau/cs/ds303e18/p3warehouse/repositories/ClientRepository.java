package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Publisher;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ClientRepository extends MongoRepository<Client, ObjectId> {


    Iterable<Client> findByPublisherId(@PathVariable ObjectId publisherId);

    Client findById(@PathVariable String hexId);


}
