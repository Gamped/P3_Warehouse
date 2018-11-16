package dk.aau.cs.ds303e18.p3warehouse;

import dk.aau.cs.ds303e18.p3warehouse.repositories.ProductRepository;
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

	@Override
	public void run(String... strings) throws Exception {

		final Cos
		final Product testProduct = new Product();
		testProduct.setName("Test Magasin").setQuantity(500).setProductId("1040");
	}
}
