package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.managers.ProductManager;
import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestClientModel;
import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestProductModel;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Customer;
import dk.aau.cs.ds303e18.p3warehouse.models.users.User;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ClientRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.OrderRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ProductRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;


@CrossOrigin
@RequestMapping("/api")
@RestController
public class ClientController {

    //TODO: updateClient opdateres i userAuth også.
    //TODO: deleteClient slettes i userAuth ogsaa.
    //TODO: Overvej hvorfor ProductManager.saveProductToDb(product, owner); kaldes i addNewProduct men ikke i updateClientProduct

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;

    @GetMapping("/clients")
    Iterable<Client> findAllClients() {return clientRepository.findAll();}

    @GetMapping("/clients/{hexId}")
    Client findClientById(@PathVariable String hexId) {
        return clientRepository.findById(new ObjectId(hexId)).orElse(null);
    }

    @GetMapping("/clients/{hexId}/products")
    private Collection<Product> findAllProductsByClient(@PathVariable String hexId) {
        Client client = clientRepository.findById(new ObjectId(hexId)).orElse(null);
        return client.getProductStream().collect(Collectors.toCollection(HashSet::new));
    }

    @GetMapping("/clients/{hexId}/orders")
    private Collection<Order>findAllOrdersByClient(@PathVariable String hexId){
        Client client = clientRepository.findById(new ObjectId(hexId)).orElse(null);
        return client.getOrderStream().collect(Collectors.toCollection(HashSet::new));
    }

    @GetMapping("/clients/products/{hexId}")
    Product findProductById(@PathVariable String hexId) {
        ObjectId objectId = new ObjectId(hexId);
        return productRepository.findById(objectId).orElse(null);
    }

    @PutMapping("/clients/{hexId}")
    String updateClient(@PathVariable("hexId") String hexId, @RequestBody RestClientModel restClientModel) {
        ObjectId id = new ObjectId(hexId);
        Client client = clientRepository.findById(id).orElse(null);
        User user = new User(id);

        BeanUtils.copyProperties(restClientModel, client);
        BeanUtils.copyProperties(client, user);
        clientRepository.save(client);
        userRepository.save(user);

        return "Client updated! \n" + client.getUserName() + "\n" + client.getHexId();
    }


    @PutMapping("/clients/products/{hexId}")
    String updateClientProduct(@PathVariable String hexId, @RequestBody RestProductModel restProduct) {
        ObjectId id = new ObjectId(hexId);
        Product product = productRepository.findById(id).orElse(null);

        BeanUtils.copyProperties(restProduct, product);
        productRepository.save(product);

        return "Product updated! \n" + product.getProductName() + "\n" + product.getHexId();
    }

    @DeleteMapping("/clients/{hexId}")
    void deleteClient(@PathVariable String hexId) {
        ObjectId id = new ObjectId(hexId);
        Client client = clientRepository.findById(id).orElse(null);

        client.unassignAllOrders().forEach(orderRepository::delete);
        client.unassignAllProducts().forEach(productRepository::save);
        User user = userRepository.findById(id).orElse(null);
        clientRepository.delete(client);
        userRepository.delete(user);
    }

    @PostMapping("/clients/{hexId}/products")
    private Product addNewProductToClient(@PathVariable String hexId, @RequestBody RestProductModel restProduct) {
        Customer owner = clientRepository.findById(new ObjectId(hexId)).orElse(null);
        Product product = new Product(new ObjectId());

        BeanUtils.copyProperties(restProduct, product);
        return ProductManager.saveProductToDb(product, owner);
    }
}
