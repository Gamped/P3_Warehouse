package dk.aau.cs.ds303e18.p3warehouse.warehouse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}