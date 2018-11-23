package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.exceptions.ProductNotFoundException;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Customer;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.ProductRequestModel;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ProductRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/products")
    private Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @PostMapping("/products")
    @CrossOrigin(origins = "*")
    private Product newProduct(@RequestBody ProductRequestModel restProduct){
        System.out.println("SUCCESS! : " + restProduct.getName());
        ObjectId id = new ObjectId();
        Product productToSave = new Product(id);
        BeanUtils.copyProperties(restProduct, productToSave);

        return productRepository.save(productToSave);
    }

    @GetMapping("/products/{id}")
    Optional<Product> findById(@PathVariable String id) {
        ObjectId objectId = new ObjectId(id);
        Optional<Product> product = productRepository.findById(objectId);
        return product;
    }

    @PutMapping("/products/edit/{id}")
    String updateProduct(@PathVariable String hexId, @RequestBody Product updatedProduct) {
       Optional<Product> product = productRepository.findByHexId(hexId);
       Product productToSave = product.get();

       //TODO: VALIDATOR CLASS IMPLEMENTATION

        System.out.println(product.toString());
       productToSave.copyParametersFrom(updatedProduct);
       productRepository.save(productToSave);
       return "Product updated! \n" + productToSave.getHexId().toString();
    }

    @DeleteMapping("/products/{id}")
    void deleteProduct(@RequestBody Product productToDelete){
        productRepository.delete(productToDelete);
    }

    @GetMapping("/clients/{id}/products")
    Iterable<Product> findByOwner(@RequestBody Customer owner) {
        return productRepository.findByOwner(owner);
    }

}
