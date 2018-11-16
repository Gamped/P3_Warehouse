package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.exceptions.ProductNotFoundException;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/products")
    private Iterable<Product> all(){
        return productRepository.findAll();
    }

    @PostMapping("/products")
    private Product newProduct(@RequestBody Product newProduct){
        return productRepository.save(newProduct);
    }

    @GetMapping("/products/{id}")
    Product one(@PathVariable String id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException());
    }
    @PutMapping("/products/{id}")
    Product replaceProduct(@RequestBody Product newProduct, @PathVariable String id){
        return null;
    }
    @DeleteMapping("/products/{id}")
    void deleteProduct(@PathVariable String id){
        productRepository.deleteById(id);
    }
}
