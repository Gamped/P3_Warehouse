package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestProductModel;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Customer;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, ObjectId> {

    List<Product> findAll();

    Optional<Product> findById(ObjectId id);
    //Delete-funktionen crashede mit spring, lav heller denne funktionalitet i en handler, der parser hexstreng om til ObjectId
    Iterable<Product> findByOwner(Customer owner);

}