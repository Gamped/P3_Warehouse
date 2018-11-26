package dk.aau.cs.ds303e18.p3warehouse.controllers;

 import dk.aau.cs.ds303e18.p3warehouse.exceptions.ProductNotFoundException;
 import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
 import dk.aau.cs.ds303e18.p3warehouse.models.users.Publisher;
 import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
 import dk.aau.cs.ds303e18.p3warehouse.repositories.ClientRepository;
 import dk.aau.cs.ds303e18.p3warehouse.repositories.EmployeeRepository;
 import dk.aau.cs.ds303e18.p3warehouse.repositories.ProductRepository;
 import dk.aau.cs.ds303e18.p3warehouse.repositories.PublisherRepository;
 import org.bson.types.ObjectId;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.*;

 import java.util.Optional;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;
    ProductRepository productRepository;
    ClientRepository clientRepository;
    PublisherRepository publisherRepository;

    @GetMapping("/employee/products")
    private Iterable<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/employee/products/{id}")
    Product findProduct(@PathVariable ObjectId id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException());
    }

    @PostMapping("/employee/products")
    Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping("/employee/products/{id}")
    Product updateProduct(@PathVariable ObjectId id, @RequestBody Product product) {
        Optional<Product> optProduct = productRepository.findById(id);
        Product p = optProduct.get();

        if (product.getProductName() != null) {
            p.setProductName(product.getProductName());
        }
        if (Integer.class.isInstance(product.getQuantity())) {
            p.setQuantity(product.getQuantity());
        }

        productRepository.save(p);
        return p;
    }


    @DeleteMapping("/employee/{id}")
    public void deleteById(@PathVariable ObjectId id) {
        employeeRepository.deleteById(id);
    }

    @GetMapping("/employee/clients")
    private Iterable<Client> findAllClients() {
        return clientRepository.findAll();
    }

    @GetMapping("/employee/publishers")
    private Iterable<Publisher> findAllPublishers() {
        return publisherRepository.findAll();
    }
}
