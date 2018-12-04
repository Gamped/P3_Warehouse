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

@RestController
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
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

    @PostMapping("/employee/products")
    Product createProduct(@RequestBody RestProductModel restProduct) {
        Product product = new Product(new ObjectId());
        BeanUtils.copyProperties(restProduct, product);
        return ProductManager.saveProductToDb(product);
    }

    @PutMapping("/employee/products/{id}")
    Product updateProduct(@PathVariable ObjectId id, @RequestBody Product product) {
        if(id.equals(product.getId().toHexString())){
            return productRepository.save(product);
        }
        else{
            return null;
        }
    }

    @DeleteMapping("/employee/products/{id}")
    public void deleteProductById(@PathVariable String id){
        Product product = productRepository.findById(new ObjectId(id)).orElse(null);
        ProductManager.removeProductFromDb(product);
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
