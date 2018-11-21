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

        Product productToSave = new Product(newProduct.getId());
        productToSave.copyParametersFrom(newProduct);
        return productRepository.save(productToSave);
    }

    @GetMapping("/products/{id}")
    Optional<Product> findById(@PathVariable String id) {
        ObjectId objectId = new ObjectId(id);
        return productRepository.findById(objectId);
    }

    @PutMapping("/products/edit/{id}")
    Product updateProduct(@RequestBody Product updatedProduct) throws ProductNotFoundException {
       Optional<Product> product = productRepository.findById(updatedProduct.getId());
       Product productToSave = product.get();

       //TODO: VALIDATOR CLASS IMPLEMENTATION

       productToSave.copyParametersFrom(updatedProduct);
       productRepository.save(productToSave);
       return productToSave;
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
