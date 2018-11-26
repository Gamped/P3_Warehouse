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

@RequestMapping("/api")
@RestController
@CrossOrigin
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
        newProduct.setProductName(restProduct.getProductName());
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
    String updateProduct(@PathVariable("hexId") String hexId, @RequestBody RestProductModel restProduct) {
        ObjectId id = new ObjectId(hexId);
       Optional<Product> optProduct = productRepository.findById(id);
       System.out.println("Object ID = " + id.toString());

       Product productToSave = optProduct.get();
       BeanUtils.copyProperties(restProduct, productToSave);
        productRepository.save(productToSave);


        //TODO: VALIDATOR CLASS IMPLEMENTATION
        System.out.println(productToSave.toString());
        System.out.println(restProduct.modelToString());
       return "Product updated! \n" + productToSave.getProductName() + "\n" + productToSave.getHexId();
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
