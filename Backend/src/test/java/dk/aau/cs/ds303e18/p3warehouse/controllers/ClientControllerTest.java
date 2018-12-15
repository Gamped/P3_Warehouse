package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestClientModel;
import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestCustomerModel;
import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestProductModel;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ClientRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ProductRepository;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static dk.aau.cs.ds303e18.p3warehouse.models.DummyClient.makeDummyClient;
import static dk.aau.cs.ds303e18.p3warehouse.models.DummyClient.makeSpecificDummyClient;
import static dk.aau.cs.ds303e18.p3warehouse.models.DummyProduct.makeDummyProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientControllerTest {

    @Autowired
    private ClientController clientController;

    @Autowired
    private EmployeeController employeeController;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    @Before
    public void dropClients(){ //Will automatically run even if you only run a specific test.
        clientRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void testFindAllClients() {
        for(int i = 0; i < 5; ++i) {
            clientRepository.save(makeDummyClient(i));
        }
        Iterable<Client> clients = clientController.findAllClients();
        int size = 0;
        for (Client client: clients) {
            ++size;
        }
        assertEquals(size, 5);
    }

    @Test
    public void testFindClientById() {
        ObjectId id = new ObjectId();
        Client client = makeSpecificDummyClient(0, id);
        clientRepository.save(client);

        Client retrievedClient = clientController.findClientById(client.getHexId());
        assertEquals(client.getId(), retrievedClient.getId());
    }

    @Test
    public void testUpdateClient() {
        Client client = makeDummyClient(0);
        clientRepository.save(client);

        RestClientModel restClientModel = new RestClientModel();
        restClientModel.setUserName("Bangerung");

        clientController.updateClient(client.getHexId(), restClientModel);

        assertEquals(clientRepository.findById(client.getHexId()).getUserName(), restClientModel.getUserName());
    }

    @Test
    public void testUpdateClientCollateralDamage(){ //Currently failed due to tested method being sub-optimal
        Client client = makeDummyClient(0);
        clientRepository.save(client);

        RestClientModel restClientModel = new RestClientModel();
        restClientModel.setUserName("Bangerung");

        clientController.updateClient(client.getHexId(), restClientModel);

        assertEquals(clientRepository.findById(client.getHexId()).getPassword(), client.getPassword());
    }

    @Test
    public void testDeleteClientById() {
        Client client = makeDummyClient(0);
        RestCustomerModel restCustomerModel = new RestCustomerModel();
        BeanUtils.copyProperties(client, restCustomerModel);
        employeeController.createClient(restCustomerModel);

        clientController.deleteClient(client.getHexId());

        assertEquals(0, clientRepository.findAll().size());
    }

    @Test
    public void testClientFindProductById() {
        Client client = makeDummyClient(0);
        Product product = makeDummyProduct(0, client);
        client.addProduct(product);

        clientRepository.save(client);
        productRepository.save(product);

        Product retrievedProduct = clientController.findProductById(product.getHexId());

        assertEquals(product.getHexId(), retrievedProduct.getHexId());
    }

    @Test
    public void testClientUpdateProduct() {
        Client client = makeDummyClient(0);
        Product product = makeDummyProduct(0, client);
        client.addProduct(product);

        clientRepository.save(client);
        productRepository.save(product);

        RestProductModel restProductModel = new RestProductModel();
        BeanUtils.copyProperties(product, restProductModel);
        restProductModel.setQuantity(999);

        clientController.updateClientProduct(product.getHexId(), restProductModel);

        assertEquals(999, productRepository.findById(product.getId()).get().getQuantity());
    }
}

