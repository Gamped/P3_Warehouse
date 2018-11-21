package dk.aau.cs.ds303e18.p3warehouse;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ProductRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "dk.aau.cs.ds303e18.p3warehouse.repositories")
@SpringBootApplication

public class P3WarehouseApplication {

	@Autowired private ProductRepository productRepository;

	public static void main(String[] args) {

		SpringApplication.run(P3WarehouseApplication.class, args);
	}
}

