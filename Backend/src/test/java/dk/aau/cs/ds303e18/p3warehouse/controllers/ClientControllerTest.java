package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestClientModel;
import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestProductModel;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.users.ContactInformation;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ClientRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ProductRepository;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientControllerTest {

    @InjectMocks
    private ClientController clientController;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void clientControllerLoads() throws Exception {
        assertThat(clientController).isNotNull();
    }

    @Before
    public void start() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllClients() {
        ObjectId id = new ObjectId();
        ObjectId objectId = new ObjectId();
        ObjectId clientId = new ObjectId();

        Client client = new Client(id);
        Client secondClient = new Client(objectId);
        Client thirdClient = new Client(clientId);

        List<Client> clientList = new LinkedList<>();
        clientList.add(client);
        clientList.add(secondClient);
        clientList.add(thirdClient);

        when(clientRepository.findAll()).thenReturn(clientList);

        Iterable<Client> clients = clientController.findAllClients();
        verify(clientRepository).findAll();
        System.out.println(clients);
        assertEquals(clientList, clients);
    }

    @Test
    public void testFindClientById() {
        ObjectId id = new ObjectId();
        Client client = new Client(id);
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));


        Client retrievedClient = clientController.findClientById(String.valueOf(client.getId()));

        verify(clientRepository).findById(client.getId());
        assertEquals(client.getId(), retrievedClient.getId());
    }

    @Test
    public void testUpdateClient() {
        ObjectId id = new ObjectId();
        Client client = new Client(id);
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setNickName("hans");
        contactInformation.setEmail("hans@ff.cc");
        contactInformation.setPhoneNumber("26497854");
        contactInformation.setAddress("misese 2");
        contactInformation.setZipCode("2689");
        RestClientModel restClient = new RestClientModel();
        client.setUserName("kelly");
        restClient.setUserName("helly");
        restClient.setContactInformation(contactInformation);

        when(clientRepository.findById(id)).thenReturn(Optional.of(client));

        System.out.println(client.getUserName());

        String updateClient = clientController.updateClient(client.getHexId(), restClient);
        verify(clientRepository).findById(id);

        assertEquals("Client updated! \n" + client.getUserName() + "\n" + client.getHexId(), updateClient);
    }

    @Test
    public void testDeleteClientById() {
        ObjectId id = new ObjectId();
        Client client = new Client(id);

        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));

        clientController.deleteClient(client.getHexId());

        verify(clientRepository).deleteById(client.getId());

        assertEquals(0, clientRepository.findAll().size());
    }

    @Test
    public void testClientFindProductById() {
        ObjectId productId = new ObjectId();
        Product product = new Product(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Product retrievedProduct = clientController.findProductById(product.getHexId());

        verify(productRepository).findById(productId);

        assertEquals(product, retrievedProduct);
    }

    @Test
    public void testClientUpdateProduct() {
        ObjectId id = new ObjectId();
        Product product = new Product(id);
        RestProductModel restProductModel = new RestProductModel();
        restProductModel.setQuantity(60);
        restProductModel.setProductName("Run");

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        String productUpdated = clientController.updateClientProduct(product.getHexId(), restProductModel);

        verify(productRepository).findById(product.getId());

        System.out.println(product);
        assertNotNull(productUpdated);
    }
}

