package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestCustomerModel;
import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestEmployeeModel;
import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestProductModel;
import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestUserModel;
import dk.aau.cs.ds303e18.p3warehouse.models.users.*;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import dk.aau.cs.ds303e18.p3warehouse.repositories.*;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    PublisherRepository publisherRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;

    // CREATE: EMPLOYEE, PRODUCTS, CLIENTS, PUBLISHERS, USERS

    @PostMapping("/employee/employees")
    String createEmployee(@RequestBody RestEmployeeModel restEmployeeModel){

        ObjectId id = new ObjectId();
        Employee employee = new Employee(id);
        BeanUtils.copyProperties(restEmployeeModel, employee);

        if(employee.isValid()) {
            employee.setUserType(UserType.EMPLOYEE);
            if(userRepository.findAll().stream().noneMatch(x -> x.getUserName().equals(employee.getUserName()))) {
                employeeRepository.save(employee);
                User user = new User(employee.getId());
                user.copyFrom(employee);
                userRepository.save(user);
                return "created!";
            } else {return "Username already taken";}
        } else return "Invalid User";
    }

    @PostMapping("/employee/products/assignTo={customerId}/withUserType={userType}")
    String createProduct(@PathVariable("customerId") String customerId, @PathVariable("userType") String userType,
                         @RequestBody RestProductModel restProduct) {

        if (userType.equals("DEFAULT") || customerId.equals("DEFAULT")) {
            return "Could not create, customerId or userType is not set!";
        }

        Product product = new Product(new ObjectId());
        BeanUtils.copyProperties(restProduct, product);

        if(UserType.valueOf(userType).equals(UserType.PUBLISHER)){
            Optional<Publisher> optionalPublisher = publisherRepository.findById(new ObjectId(customerId));
            Publisher publisher = optionalPublisher.get();
            publisher.addProduct(product);
            product.setOwner(publisher);
            publisherRepository.save(publisher);
        } else if(UserType.valueOf(userType).equals(UserType.CLIENT)) {
            Client client = clientRepository.findById(customerId);
            client.addProduct(product);
            product.setOwner(client);
            clientRepository.save(client);
        }

        productRepository.save(product);
        return "Created!";
    }

    @PostMapping("/employee/publishers")
    String createPublisher(@RequestBody RestCustomerModel restCustomerModel) {

        Publisher publisher = new Publisher(new ObjectId());
        User user = new User(publisher.getId());

        publisher.setUserType(UserType.PUBLISHER);
        BeanUtils.copyProperties(restCustomerModel, publisher);
        BeanUtils.copyProperties(publisher, user);

        if (publisherRepository.findAll().stream().noneMatch(x -> x.getUserName().equals(publisher.getUserName()))) {
            if (publisher.isValid()) {
                publisherRepository.save(publisher);
                userRepository.save(user);

                return "Created!";
            } else return "Invalid User";
        }
        return "Username already taken";
    }

    @PostMapping("/employee/clients")
    String createClient(@RequestBody RestCustomerModel restCustomerModel) {

        Client client = new Client(new ObjectId());
        User user = new User(client.getId());

        BeanUtils.copyProperties(restCustomerModel, client);
        BeanUtils.copyProperties(restCustomerModel, user);

        client.setUserType(UserType.CLIENT);
        user.setUserType(UserType.CLIENT);
        if(clientRepository.findAll().stream().noneMatch(x -> x.getUserName().equals(client.getUserName()))) {
            if (client.isValid()) {
                userRepository.save(user);
                clientRepository.save(client);
                return client.getHexId();
            } else return "Invalid User";
        }
        return "Username already taken";
    }

    @PostMapping("/employee/publishers/{publisherId}/clients")
    String createPublisherClient(@RequestBody RestCustomerModel restCustomerModel, @PathVariable String publisherId) {

        Client client = new Client(new ObjectId());
        User user = new User(client.getId());
        Publisher publisher = null;

        try {
            publisher = publisherRepository.findById(new ObjectId(publisherId)).orElseThrow(() -> new Exception(publisherId));
        } catch(Exception e) {
            return "Could not find publisher on id: " + publisherId;
        }

        BeanUtils.copyProperties(restCustomerModel, client);
        BeanUtils.copyProperties(restCustomerModel, user);

        client.setUserType(UserType.CLIENT);
        user.setUserType(UserType.CLIENT);
        client.setPublisher(publisher);
        publisher.addClient(client);

        if(clientRepository.findAll().stream().noneMatch(x -> x.getUserName().equals(client.getUserName()))) {
            if (client.isValid()) {
                userRepository.save(user);
                clientRepository.save(client);
                publisherRepository.save(publisher);
                return "Created client: " + client.getHexId() + " with reference to publisher: " + publisher.getId();
            } else return "Invalid User";
        }
        return "Username already taken";
    }

    //FIND ALL: EMPLOYEE, PRODUCTS, CLIENTS, PUBLISHERS, USERS

    @GetMapping("/employee/employees")
    private Collection<Employee> getAllEmployees() {return employeeRepository.findAll();}

    @GetMapping("/employee/products")
    Collection<Product> findAllProducts() {return productRepository.findAll();}

    @GetMapping("/employee/clients")
    private Collection<Client> findAllClients() {return clientRepository.findAll();}

    @GetMapping("/employee/publishers")
    private Collection<Publisher> findAllPublishers() {return publisherRepository.findAll();}

    @GetMapping("/employee/users")
    Collection<User> findAllUsers() {return userRepository.findAll();}

    //FIND BY ID: EMPLOYEE, PRODUCTS, CLIENTS, PUBLISHERS, USERS

    @GetMapping("/employee/employee/{hexId}")
    Employee findEmployeeById(@PathVariable String hexId){

        return employeeRepository.findById(new ObjectId(hexId)).orElse(null);
    }

    @GetMapping("/employee/product/{hexId}")
    Optional<Product> findProductById(@PathVariable String hexId) {

        return productRepository.findById(new ObjectId(hexId));
    }

    @GetMapping("/employee/publisher/{hexId}")
    private Optional<Publisher> findPublisherById(@PathVariable String hexId) {

        return publisherRepository.findById(new ObjectId(hexId));
    }

    @GetMapping("/employee/client/{hexId}")
    private Optional<Client> findClientById(@PathVariable String hexId) {

        return clientRepository.findById(new ObjectId(hexId));
    }

    @GetMapping("employee/order/{hexId}")
    private Optional<Order> findOrderById(@PathVariable String hexId) {

        return orderRepository.findById(new ObjectId(hexId));
    }

    //UPDATE: EMPLOYEE, PRODUCTS, CONTACT INFORMATION (CLIENT, PUBLISHER), USERS

    @PutMapping("/employee/edit/{hexId}")
    String updateEmployee(@PathVariable("hexId") String hexId, @RequestBody RestEmployeeModel restEmployeeModel) {

        Optional<Employee> optionalEmployee = employeeRepository.findById(new ObjectId(hexId));
        Employee employee = optionalEmployee.get();

        Optional<User> optionalUser = userRepository.findById(new ObjectId(hexId));
        User user = optionalUser.get();

        BeanUtils.copyProperties(restEmployeeModel, employee);
        BeanUtils.copyProperties(restEmployeeModel, user);
        employeeRepository.save(employee);
        userRepository.save(user);

        return "Updated Employee with nickName ";
    }

    @PutMapping("/employee/product/edit/{hexId}")
    String updateProduct(@PathVariable String hexId, @RequestBody RestProductModel restProduct) {

        ObjectId id = new ObjectId(hexId);
        Optional<Product> optProduct = productRepository.findById(id);
        Product product = optProduct.get();

        BeanUtils.copyProperties(restProduct, product);
        productRepository.save(product);

        return "Updated Product: " + product.toString();
    }

    @PutMapping("/employee/client/contactInformation/edit/{hexId}")
    String updateContactInformationOnClient(@PathVariable String hexId, @RequestBody ContactInformation contactInformation) {

        ObjectId id = new ObjectId(hexId);
        Optional<Client> optClient = clientRepository.findById(id);
        Client client = optClient.get();
        ContactInformation oldContactInformation = client.getContactInformation();

        BeanUtils.copyProperties(contactInformation, oldContactInformation);
        clientRepository.save(client);

        return "Updated Contact Information on Client: " + client.getUserName();
    }

    @PutMapping("/employee/publisher/contactInformation/edit/{hexId}")
    String updateContactInformationOnPublisher(@PathVariable String hexId, @RequestBody ContactInformation contactInformation) {

        ObjectId id = new ObjectId(hexId);
        Optional<Publisher> optPublisher = publisherRepository.findById(id);
        Publisher publisher = optPublisher.get();
        ContactInformation oldContactInformation = publisher.getContactInformation();

        BeanUtils.copyProperties(contactInformation, oldContactInformation);
        publisherRepository.save(publisher);

        return "Updated Contact Information on Publisher: " + publisher.getUserName();
    }

    @PutMapping("/employee/user/edit/{hexId}")
    String updateUserCredentials(@PathVariable String hexId, @RequestBody RestUserModel restUserModel) {

        ObjectId id = new ObjectId(hexId);
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.get();

        BeanUtils.copyProperties(restUserModel, user);
        userRepository.save(user);

        return "Updated user: " + user.getUserName();
    }

    //DELETE: EMPLOYEE, PRODUCT, CLIENT, PUBLISHER, USER

    @DeleteMapping("/employee/products/delete/{hexId}")
    public void deleteProductById(@PathVariable String hexId){

        Product product = productRepository.findById(new ObjectId(hexId)).get();
        if(product.getOwner().getUserType().equals(UserType.CLIENT)){
            Client client = clientRepository.findById(product.getOwner().getHexId());

            client.removeProduct(product);
            clientRepository.save(client);
        } else if(product.getOwner().getUserType().equals(UserType.PUBLISHER)) {
            Publisher publisher = publisherRepository.findById(product.getOwner().getHexId()).get();

            publisher.removeProduct(product);
            publisherRepository.save(publisher);
        }
        productRepository.deleteById(new ObjectId(hexId));
    }

    @DeleteMapping("/employee/delete/{hexId}")
    public String deleteEmployeeById(@PathVariable String hexId) {

        ObjectId id = new ObjectId(hexId);
        if(!employeeRepository.existsById(id)) {
            return "Employee does not exist in employee repository";
        }
        
        employeeRepository.deleteById(id);
        userRepository.deleteById(id);
        return "Deletion Success";
    }

    @DeleteMapping("/employee/clients/delete/{hexId}")
    private void deleteClientById(@PathVariable String hexId) {

        ObjectId id = new ObjectId(hexId);
        Client client = clientRepository.findById(id).orElse(null);

        client.unassignAllOrders().forEach(orderRepository::delete);
        client.unassignAllProducts().forEach(productRepository::save);
        User user = userRepository.findById(id).orElse(null);
        if (client.getPublisher() != null) {
            Publisher publisher = publisherRepository.findById(client.getPublisher().getHexId()).orElse(null);
            publisher.removeClient(client);
            publisherRepository.save(publisher);
        }
        clientRepository.delete(client);
        userRepository.delete(user);
    }

    @DeleteMapping("/employee/publishers/delete/{hexId}")
    private void deletePublisherById(@PathVariable String hexId) {

        ObjectId id = new ObjectId(hexId);

        Publisher publisher = publisherRepository.findById(id).orElse(null);
        publisher.getClientStream().map(x -> x.unassignAllProducts()).forEach(x -> x.forEach(productRepository::delete));
        publisher.getClientStream().map(x -> x.unassignAllOrders()).forEach(x -> x.forEach(orderRepository::delete));
        publisher.unassignAllOrders().forEach(orderRepository::delete);
        publisher.unassignAllProducts().forEach(productRepository::delete);
        publisher.getClientStream().forEach(clientRepository::delete);
        publisher.getClientStream().forEach(userRepository::delete);
        publisherRepository.delete(publisher);
        User user = userRepository.findById(publisher.getId()).orElse(null);
        userRepository.delete(user);
    }

    @DeleteMapping("/employee/users/delete/{hexId")
    private void deleteUserById(@PathVariable String hexId) {userRepository.deleteById(new ObjectId(hexId));}

    //CLIENT PUBLISHER ROUTES

    @GetMapping("/employee/publishers/addClient={clientHexId}/toPublisher={publisherHexId}")
    private String addClientToPublisher(@PathVariable("clientHexId") String clientHexId,
                                        @PathVariable("publisherHexId") String publisherHexId) {

        Optional<Publisher> optionalPublisher = publisherRepository.findById(new ObjectId(publisherHexId));
        Optional<Client> optionalClient = clientRepository.findById(new ObjectId(clientHexId));
        Publisher publisher = optionalPublisher.get();
        Client client = optionalClient.get();
        client.setPublisher(publisher);
        publisher.addClient(client);
        publisherRepository.save(publisher);
        return "Client " + client.getUserName() + " Added to publisher " + publisher.getUserName();
    }

    @PostMapping("/employee/publishers/removeClient={clientHexId}/fromPublisher={publisherHexId}")
    private String removeClientToPublisher(@PathVariable("clientHexId") String clientHexId,
                                        @PathVariable("publisherHexId") String publisherHexId) {

        Optional<Publisher> optionalPublisher = publisherRepository.findById(new ObjectId(publisherHexId));
        Optional<Client> optionalClient = clientRepository.findById(new ObjectId(clientHexId));
        Publisher publisher = optionalPublisher.get();
        Client client = optionalClient.get();

        publisher.removeClient(client);
        publisherRepository.save(publisher);
        return "Client " + client.getUserName() + " Added to publisher " + publisher.getUserName();
    }
}