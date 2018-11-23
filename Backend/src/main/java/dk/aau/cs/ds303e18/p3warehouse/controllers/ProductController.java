package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.users.Customer;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestProductModel;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ProductRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/products/new")
    private Product newProduct(@RequestBody RestProductModel restProduct) {

        ObjectId id = new ObjectId();
        Product newProduct = new Product(id);
        BeanUtils.copyProperties(restProduct, newProduct);

        return productRepository.save(newProduct);
    }

    @GetMapping("/products/{id}")
    Optional<Product> findById(@PathVariable String id) {
        ObjectId objectId = new ObjectId(id);
        Optional<Product> product = productRepository.findById(objectId);

        return product;
    }

    @PutMapping("/products/edit/{hexId}")
    String updateProduct(@PathVariable String hexId, @RequestBody RestProductModel restProductModel) {

       Optional<Product> optProduct = productRepository.findByHexId(hexId);

       Product productToSave = optProduct.get();
       BeanUtils.copyProperties(restProductModel, productToSave);
        productRepository.save(productToSave);


        //TODO: VALIDATOR CLASS IMPLEMENTATION

       return "Product updated! \n" + productToSave.getName() + "\n" + productToSave.getHexId();
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
