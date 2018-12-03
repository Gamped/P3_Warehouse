package dk.aau.cs.ds303e18.p3warehouse.controllers;

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
    private Iterable<Client> findAll() {
        return clientRepository.findAll();
    }


    @GetMapping("/clients/products")
    private Iterable<Client> findAllProducts() {
        return clientRepository.findAll();
    }

    @GetMapping("clients/products/{id}")
    Client findById(@PathVariable String id) {

        ObjectId objectId = new ObjectId(id);
        Client product = clientRepository.findById(objectId).orElse(null);

        return product;
    }

    @GetMapping("/clients/{id}")
    Client findById(@PathVariable ObjectId id) { return clientRepository.findById(id).orElse(null);
    }

    @PostMapping("/clients/{publisherId}/new")
    private Client newClient(@PathVariable String publisherId, @RequestBody RestClientModel restClientModel) {

        ObjectId id = new ObjectId();
        Client newClient = new Client(id);
        //newClient.setClientName(restClientModel.getClientName());
        BeanUtils.copyProperties(restClientModel, newClient);

        return clientRepository.save(newClient);
    }

    @PutMapping("/client/products/{hexId}")
    String update(@PathVariable String hexId, @RequestBody RestProductModel restProduct) {

        ObjectId id = new ObjectId(hexId);
        Product product = productRepository.findById(id).orElse(null);
        BeanUtils.copyProperties(restProduct, product);
        productRepository.save(product);

        return "Product updated! \n" + product.getProductName() + "\n" + product.getHexId();
    }


    @PutMapping("/clients/edit/{hexId}")
    String update(@PathVariable("hexId") String hexId, @RequestBody RestClientModel restClientModel) {

        ObjectId id = new ObjectId(hexId);
        Client client = clientRepository.findById(id).orElse(null);


        BeanUtils.copyProperties(restClientModel, client);

        clientRepository.save(client);

        return "Publisher updated! \n" + client.getClientName() + "\n" + client.getHexId();
    }


    @DeleteMapping("/clients/delete/{id}")
    void delete(@PathVariable String hexId) {
        ObjectId id = new ObjectId(hexId);

        clientRepository.deleteById(id);
    }

}