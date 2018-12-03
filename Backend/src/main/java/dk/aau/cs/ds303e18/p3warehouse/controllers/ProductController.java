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

    @GetMapping("/products/{id}")
    Product findById(@PathVariable String id) {

        ObjectId objectId = new ObjectId(id);
        Product product = productRepository.findById(objectId).orElse(null);

        return product;
    }

    @PostMapping("/products/new")
    private Product newProduct(@RequestBody RestProductModel restProduct) {

        ObjectId id = new ObjectId();
        Product newProduct = new Product(id);
        newProduct.setProductName(restProduct.getProductName());
        BeanUtils.copyProperties(restProduct, newProduct);

        return productRepository.save(newProduct);
    }

    @PutMapping("/products/edit/{hexId}")
    String update(@PathVariable("hexId") String hexId, @RequestBody RestProductModel restProduct) {

       ObjectId id = new ObjectId(hexId);
       Product product = productRepository.findById(id).orElse(null);

       BeanUtils.copyProperties(restProduct, product);

       productRepository.save(product);
       return "Product updated! \n" + product.getProductName() + "\n" + product.getHexId();
    }

    @DeleteMapping("/products/delete/{id}")
    void delete(@PathVariable String hexId) {
        ObjectId id = new ObjectId(hexId);
        productRepository.deleteById(id);
    }

    @GetMapping("/clients/{id}/products")
    Iterable<Product> findByOwner(@RequestBody Customer owner) {
        return productRepository.findByOwner(owner);
    }


}
