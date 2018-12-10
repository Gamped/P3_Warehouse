package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.managers.ProductManager;
import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestClientModel;
import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestProductModel;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Customer;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ClientRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ProductRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;


@RequestMapping("/api")
@RestController
@CrossOrigin
public class ClientController {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ProductRepository productRepository;

    @GetMapping("/clients")
    Iterable<Client> findAllClients() {
        return clientRepository.findAll();
    }

    @GetMapping("/clients/{id}")
    Client findClientById(@PathVariable ObjectId id) { return clientRepository.findById(id).orElse(null);
    }


    @PutMapping("/clients/{hexId}")
    String updateClient(@PathVariable("hexId") String hexId, @RequestBody RestClientModel restClientModel) {

        ObjectId id = new ObjectId(hexId);
        Client client = clientRepository.findById(id).orElse(null);
        BeanUtils.copyProperties(restClientModel, client);
        clientRepository.save(client);

        return "Client updated! \n" + client.getUserName() + "\n" + client.getHexId();
    }

    @DeleteMapping("/clients/{id}")
    void deleteClient(@PathVariable String hexId) {
        ObjectId id = new ObjectId(hexId);

        clientRepository.deleteById(id);
    }

    @PostMapping("/clients/{hexId}/products")
    private Product addNewProductToClient(@PathVariable String hexId, @RequestBody RestProductModel restProduct){
        Customer owner = clientRepository.findById(new ObjectId(hexId)).orElse(null);
        Product product = new Product(new ObjectId());
        
        BeanUtils.copyProperties(restProduct, product);
        return ProductManager.saveProductToDb(product, owner);
    }

    @GetMapping("/clients/{hexId}/products")
    private Collection<Product> findAllProductsByClient(@PathVariable String hexId) {
        Client client = clientRepository.findById(hexId);
        return client.getProductStream().collect(Collectors.toCollection(HashSet::new));
    }

    @GetMapping("/clients/products/{id}")
    Product findProductById(@PathVariable String id) {

        ObjectId objectId = new ObjectId(id);
        return productRepository.findById(objectId).orElse(null);
    }

    @PutMapping("/clients/products/{hexId}")
    String updateClientProduct(@PathVariable String hexId, @RequestBody RestProductModel restProduct) {

        ObjectId id = new ObjectId(hexId);
        Product product = productRepository.findById(id).orElse(null);
        BeanUtils.copyProperties(restProduct, product);
        productRepository.save(product);

        return "Product updated! \n" + product.getProductName() + "\n" + product.getHexId();
    }
}
