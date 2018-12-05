package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.managers.ClientManager;
import dk.aau.cs.ds303e18.p3warehouse.managers.ProductManager;
import dk.aau.cs.ds303e18.p3warehouse.managers.UserManager;
import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestClientModel;
import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestProductModel;
import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestPublisherModel;
import dk.aau.cs.ds303e18.p3warehouse.models.users.*;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ProductRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.PublisherRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.web.bind.annotation.*;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ClientRepository;

import javax.validation.constraints.Null;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


import java.util.Collection;
import java.util.List;
import java.util.Optional;


@RequestMapping("/api")
@RestController
@CrossOrigin
public class ClientController {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ProductRepository productRepository;

    @GetMapping("/clients")
    private Collection<Client> findAllClients() {
        return clientRepository.findAll();
    }

    @GetMapping("/clients/{id}")
    Client findClientById(@PathVariable ObjectId id) { return clientRepository.findById(id).orElse(null);
    }

    @PostMapping("/clients")
    private Client newIndependentClient( @RequestBody RestClientModel restClientModel) {

        Client newClient = new Client(new ObjectId());
        BeanUtils.copyProperties(restClientModel, newClient);
        return clientRepository.save(newClient);
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
        product.setOwner(owner);
        BeanUtils.copyProperties(restProduct, product);
        return ProductManager.saveProductToDb(product);
    }

    @GetMapping("/clients/{hexId}/products")
    private Collection<Product> findAllProductsByClient(@PathVariable String hexId) {
        Client client = clientRepository.findById(hexId);
        return productRepository.findAllByOwner(client);
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