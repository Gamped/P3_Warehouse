package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.exceptions.ProductNotFoundException;
import dk.aau.cs.ds303e18.p3warehouse.warehouse.Product;
import dk.aau.cs.ds303e18.p3warehouse.warehouse.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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

        // Conditionals to check for null
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(newProduct.getName());
                    product.setQuantity(newProduct.getQuantity());
                    return productRepository.save(product);
                        })
                .orElseGet(() -> {
                    newProduct.setId(id);
                    return productRepository.save(newProduct);
                });
    }
    @DeleteMapping("/products/{id}")
    void deleteProduct(@PathVariable String id){
        productRepository.deleteById(id);
    }
}
