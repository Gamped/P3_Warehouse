package dk.aau.cs.ds303e18.p3warehouse.controllers;

 import dk.aau.cs.ds303e18.p3warehouse.exceptions.ProductNotFoundException;
 import dk.aau.cs.ds303e18.p3warehouse.managers.EmployeeManager;
 import dk.aau.cs.ds303e18.p3warehouse.managers.ProductManager;
 import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestProductModel;
 import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
 import dk.aau.cs.ds303e18.p3warehouse.models.users.Employee;
 import dk.aau.cs.ds303e18.p3warehouse.models.users.Publisher;
 import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
 import dk.aau.cs.ds303e18.p3warehouse.repositories.ClientRepository;
 import dk.aau.cs.ds303e18.p3warehouse.repositories.EmployeeRepository;
 import dk.aau.cs.ds303e18.p3warehouse.repositories.ProductRepository;
 import dk.aau.cs.ds303e18.p3warehouse.repositories.PublisherRepository;
 import org.bson.types.ObjectId;
 import org.springframework.beans.BeanUtils;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.*;

 import java.util.Collection;
 import java.util.Optional;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;
    ProductRepository productRepository;
    ClientRepository clientRepository;
    PublisherRepository publisherRepository;

    @PostMapping("/employee")
    private Employee newEmployee(@RequestBody Employee employee){
        return EmployeeManager.saveEmployeeToDb(employee);
    }

    @GetMapping("/employee")
    private Collection<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/employee/{hexId}")
    private Employee getOneEmployee(@PathVariable String hexId){
        return employeeRepository.findById(new ObjectId(hexId)).orElse(null);
    }
    @GetMapping("/employee/products")
    private Collection<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/employee/products/{id}")
    private Product findProduct(@PathVariable ObjectId id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException());
    }

    @PostMapping("/employee/products")
    Product createProduct(@RequestBody RestProductModel restProduct) {
        Product product = new Product(new ObjectId());
        BeanUtils.copyProperties(restProduct, product);
        return ProductManager.addProductToDb(product);
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
    private Collection<Client> findAllClients() {
        return clientRepository.findAll();
    }

    @GetMapping("/employee/publishers")
    private Collection<Publisher> findAllPublishers() {
        return publisherRepository.findAll();
    }
}
