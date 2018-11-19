package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.exceptions.ProductNotFoundException;
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
        return null;
    }

    @PostMapping("/products")
    private Product newProduct(@RequestBody Product newProduct){
        return productRepository.save(newProduct);
    }

    @GetMapping("/products/{id}")
    Optional<Product> findById(@PathVariable ObjectId id) {
        return productRepository.findById(id);
    }

    @PutMapping("/products/{id}")
    Product replaceProduct(@RequestBody Product newProduct, @PathVariable String id){
        return null;
    }

    @DeleteMapping("/products/{id}")
    void deleteProduct(@PathVariable String id){
    }

    @GetMapping("/clients/{id}/products")
    List<Product> findByClientId(@PathVariable ObjectId clientId) {
        return productRepository.findByClientId(clientId);
    }

}
