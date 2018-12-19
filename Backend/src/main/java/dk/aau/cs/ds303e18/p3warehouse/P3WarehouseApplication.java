package dk.aau.cs.ds303e18.p3warehouse;

import dk.aau.cs.ds303e18.p3warehouse.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableMongoRepositories(basePackages = "dk.aau.cs.ds303e18.p3warehouse.repositories")
@EnableWebSecurity
@SpringBootApplication
public class P3WarehouseApplication {

	@Autowired
	private ProductRepository productRepository;

	public static void main(String[] args) {SpringApplication.run(P3WarehouseApplication.class, args);}
}