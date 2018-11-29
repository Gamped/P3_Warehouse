package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestClientModel;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Publisher;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ClientRepository;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientControllerTest {

    @InjectMocks
    private ClientController clientController;

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
    public void testClientFindById() {
        ObjectId id = new ObjectId();
        Client client = new Client(id);
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));

        Optional<Client> optClient = clientController.findById(id);
        Client retrievedClient = optClient.get();
        verify(clientRepository).findById(id);
        assertEquals(client.getId(), retrievedClient.getId());
    }

    @Test
    public void testUpdateClient() {
        ObjectId id = new ObjectId();
        Client client = new Client(id);
        RestClientModel restClient = new RestClientModel();
        client.setClientName("kelly");

        when(clientRepository.findById(id)).thenReturn(Optional.of(client));

        String updateClient = clientController.update(client.getHexId(), restClient);
        String clientString = client.toString();

        assertTrue(updateClient.contains(client.getHexId()));
    }

    @Test
    public void testDeleteClientById() {
        ObjectId id = new ObjectId();
        Client client = new Client(id);

        when(clientRepository.findById(id)).thenReturn(Optional.of(client));

        clientController.delete(client.getHexId());
        verify(clientRepository).deleteById(id);

        List<Client> clients = new ArrayList<>();
        assertEquals(clients, clientRepository.findAll());
    }

    @Test
    public void testFindClientByPublisherId() {
        ObjectId publisherId = new ObjectId();
        Publisher publisher = new Publisher(publisherId);

        when(clientRepository.findByPublisherId(publisherId)).thenReturn(publisher);

        Publisher byPublisherId = clientController.clientRepository.findByPublisherId(publisherId);
        verify(clientRepository).findByPublisherId(publisherId);
        assertEquals(publisher.getId() , byPublisherId.getId());
    }
}
