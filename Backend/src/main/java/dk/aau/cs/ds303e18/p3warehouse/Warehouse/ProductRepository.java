package dk.aau.cs.ds303e18.p3warehouse.Warehouse;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
