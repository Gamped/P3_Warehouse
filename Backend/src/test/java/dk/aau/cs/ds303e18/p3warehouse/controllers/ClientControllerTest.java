package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.managers.ClientManager;
import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestClientModel;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.users.ContactInformation;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ClientRepository;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientControllerTest {

    @InjectMocks
    private ClientController clientController;

    private EmployeeController employeeController;
    @Mock
    private ClientRepository clientRepository;

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
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));

        Client retrievedClient = clientController.findClientById(id);
        verify(clientRepository).findById(id);
        assertEquals(client.getId(), retrievedClient.getId());
    }
    /*
    @Test
    public void testNewIndependentClient() {
        //ObjectId id = new ObjectId();
        Client client = new Client(new ObjectId());
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setPhoneNumber("285646");
        contactInformation.setEmail("mille.vo@omd.gg");
        RestClientModel restClientModel = new RestClientModel();
        client.setContactInformation(contactInformation);
        restClientModel.setUserName("mille");
        restClientModel.setPassword("1234");
        BeanUtils.copyProperties(client, restClientModel);

        when(clientRepository.save(client)).thenReturn(client);

        String status = employeeController.createClient(restClientModel);

        verify(clientRepository).save(client);
        assertEquals("Succes", status);
    }
    */

    @Test
    public void testUpdateClient() {
        ObjectId id = new ObjectId();
        Client client = new Client(id);
        RestClientModel restClient = new RestClientModel();
        client.setUserName("kelly");
        restClient.setUserName("helly");

        when(clientRepository.findById(id)).thenReturn(Optional.of(client));

        System.out.println(client.getUserName());

        String updateClient = clientController.updateClient(client.getHexId(), restClient);
        verify(clientRepository).findById(id);
        String clientString = client.toString();

        assertEquals(clientString, updateClient);
    }

    @Test
    public void testDeleteClientById() {
        ObjectId id = new ObjectId();
        ObjectId objectId = new ObjectId();
        Client client = new Client(id);
        Client client_2 = new Client(objectId);

        List<Client> clients = new LinkedList<>();
        clients.add(client);
        clients.add(client_2);

        when(clientRepository.findAll()).thenReturn(clients);

        clientController.deleteClient(((LinkedList<Client>) (clients)).remove().getHexId());
        verify(clientRepository).deleteById(id);

        List<Client> clientss = new ArrayList<>();

        assertEquals(clientss, clientRepository.findAll());
    }
}
/*    @Test
    public void testFindClientByPublisherId() {
        ObjectId publisherId = new ObjectId();
        Publisher publisher = new Publisher(publisherId);

        when(clientRepository.findByPublisherId(publisherId)).thenReturn(publisher);

        Publisher byPublisherId = clientController.clientRepository.findByPublisherId(publisherId);
        verify(clientRepository).findByPublisherId(publisherId);
        assertEquals(publisher.getId() , byPublisherId.getId());
    }
}*/
