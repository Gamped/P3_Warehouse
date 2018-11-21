package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.exceptions.ProductNotFoundException;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Customer;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ProductRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/products")
    private Iterable<Product> findAll(){
        return productRepository.findAll();
    }

    @PostMapping("/products")
    private Product newProduct(@RequestBody Product newProduct){

        Product productToSave = new Product(new ObjectId());
        productToSave.copyParametersFrom(newProduct);
        return productRepository.save(productToSave);
    }

    @GetMapping("/products/{id}")
    Optional<Product> findById(@PathVariable ObjectId id) {
        return productRepository.findById(id);
    }

    @PutMapping("/products/")
    Product updateProduct(@RequestBody Product updatedProduct){
       Product query = productRepository.findById(updatedProduct.getId()).orElse(null);
        if(query.equals(null)){
            //TODO: Throw exception
            return null;
        }
        else{
            return productRepository.save(updatedProduct);
        }
    }

    @DeleteMapping("/products/{id}")
    void deleteProduct(@RequestBody Product productToDelete){
        productRepository.delete(productToDelete);
    }

    @GetMapping("/clients/{id}/products")
    List<Product> findByOwner(@RequestBody Customer owner) {
        return productRepository.findByOwner(owner);
    }

}
