package dk.aau.cs.ds303e18.p3warehouse.controllers;

 import dk.aau.cs.ds303e18.p3warehouse.exceptions.ProductNotFoundException;
 import dk.aau.cs.ds303e18.p3warehouse.managers.EmployeeManager;
 import dk.aau.cs.ds303e18.p3warehouse.managers.ProductManager;
 import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
 import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.*;
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


    //CREATE: EMPLOYEE, PRODUCTS, CLIENTS, PUBLISHERS, USERS


    @PostMapping("/employee/employees")
    private String createEmployee(@RequestBody RestEmployeeModel restEmployeeModel){
        ObjectId id = new ObjectId();
        Employee employee = new Employee(id);
        BeanUtils.copyProperties(restEmployeeModel, employee);
        employeeRepository.save(employee);
        return "created!";
    }

    @PostMapping("/employee/products/assignTo={customerId}/withUserType={userType}")
    String createProduct(@PathVariable("customerId") String customerId, @PathVariable("userType") String userType,
                         @RequestBody RestProductModel restProduct) {

        if (userType == "DEFAULT" || customerId == "DEFAULT") {
            return "Could not create, customerId or userType is not set!";
        }


        Product product = new Product(new ObjectId());
        BeanUtils.copyProperties(restProduct, product);

        Optional<Publisher> optionalPublisher = publisherRepository.findById(new ObjectId(customerId));
        Publisher publisher = optionalPublisher.get();
        publisher.addProduct(product);
        publisherRepository.save(publisher);
        product.setOwner(publisher);


        if (userType == "CLIENT") {
            Optional<Client> optionalClient = clientRepository.findById(new ObjectId(customerId));
            Client client = optionalClient.get();
            client.addProduct(product);
            clientRepository.save(client);
            product.setOwner(client);

        }
        productRepository.save(product);

        return "Created!";
    }

    @PostMapping("/employee/publishers")
    String createPublisher(@RequestBody RestCustomerModel restCustomerModel) {

        Publisher publisher = new Publisher(new ObjectId());
        publisher.setUserType(UserType.PUBLISHER);
        BeanUtils.copyProperties(restCustomerModel, publisher);
        publisherRepository.save(publisher);
        User user = new User(publisher.getId());
        user.copyFrom(publisher);
        userRepository.save(user);
        return "Created!";
    }

    @PostMapping("/employee/clients")
    String createClient(@RequestBody RestCustomerModel restCustomerModel) {

        Client client = new Client(new ObjectId());
        BeanUtils.copyProperties(restCustomerModel, client);
        client.setUserType(UserType.CLIENT);
        userRepository.save(client);
        clientRepository.save(client);
        return "Created!";
    }

    //FIND ALL: EMPLOYEE, PRODUCTS, CLIENTS, PUBLISHERS, USERS

    @GetMapping("/employee/employees")
    private Collection<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/employee/products")
    Collection<Product> findAllProducts() {
        return productRepository.findAll();
    }


    @GetMapping("/employee/clients")
    private Collection<Client> findAllClients() {
        return clientRepository.findAll();
    }

    @GetMapping("/employee/publishers")
    private Collection<Publisher> findAllPublishers() {
        return publisherRepository.findAll();
    }


    @GetMapping("/employee/users")
    Collection<User> findAllUsers() {
        return userRepository.findAll();
    }

    //FIND BY ID: EMPLOYEE, PRODUCTS, CLIENTS, PUBLISHERS, USERS

    @GetMapping("/employee/{hexId}")
    Employee getOneEmployee(@PathVariable String hexId){
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

    //UPDATE: EMPLOYEE, PRODUCTS, CONTACT INFORMATION (CLIENT, PUBLISHER), USERS

    @PutMapping("/employee/edit/{hexId}")
    String updateEmployee(@PathVariable("hexId") String hexId, @RequestBody RestEmployeeModel restEmployeeModel) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(new ObjectId(hexId));
        Employee employee = optionalEmployee.get();
        employee.setNickname(restEmployeeModel.getNickname());
        employeeRepository.save(employee);

        return "Updated Employee with nickName " + employee.getNickname();
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

    @PutMapping("/employee/user/edit/{hexId")
    String updateUserCredentials(@PathVariable String hexId, @RequestBody RestUserModel restUserModel) {

        ObjectId id = new ObjectId(hexId);

        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.get();
        BeanUtils.copyProperties(restUserModel, user);
        userRepository.save(user);

        return "Updated user: " + user.getUserName();
    }

    //DELETE: EMPLOYEE, PRODUCT, CLIENT, PUBLISHER, USER

    @DeleteMapping("/employee/products/delete/{id}")
    public void deleteProductById(@PathVariable String hexId){
        productRepository.deleteById(new ObjectId(hexId));
    }


    @DeleteMapping("/employee/delete/{hexId}")
    public String deleteEmployeeById(@PathVariable String hexId, String employeeName, String password) {
        ObjectId id = new ObjectId(hexId);
        if(!employeeRepository.existsById(id)){ //Prevents the deleter from deleting if the deleter is not in the database.
            return "Unauthorized action";
        }
        if(password.equals(employeeRepository.findById(id).get().getPassword())){
            employeeRepository.deleteById(employeeRepository.findByNickname(employeeName).getId());
            return "Deletion Success";
        }
        return "Deletion failed";
    }


    @DeleteMapping("/employee/clients/delete/{hexId}")
    private void deleteClientById(@PathVariable String hexId) {
        clientRepository.deleteById(new ObjectId(hexId));
    }

    @DeleteMapping("/employee/publishers/delete/{hexId}")
    private void deletePublisherById(@PathVariable String hexId) {
        publisherRepository.deleteById(new ObjectId(hexId));
    }

    @DeleteMapping("/employee/users/delete/{hexId")
    private void deleteUserById(@PathVariable String hexId) {
        userRepository.deleteById(new ObjectId(hexId));
    }


    //CLIENT PUBLISHER ROUTES

    @PostMapping("/employee/publishers/addClient={clientHexId}/toPublisher={publisherHexId}")
    private String addClientToPublisher(@PathVariable("clientHexId") String clientHexId,
                                        @PathVariable("publisherHexId") String publisherHexId) {

        Optional<Publisher> optionalPublisher = publisherRepository.findById(new ObjectId(publisherHexId));
        Optional<Client> optionalClient = clientRepository.findById(new ObjectId(clientHexId));
        Publisher publisher = optionalPublisher.get();
        Client client = optionalClient.get();
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

