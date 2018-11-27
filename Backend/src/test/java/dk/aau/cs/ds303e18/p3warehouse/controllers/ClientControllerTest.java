package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
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
        when(clientRepository.findById(id)).thenReturn(Optional.ofNullable(client));

        Optional<Client> optClient = clientController.findById(id);
        Client retrievedClient = optClient.get();
        verify(clientRepository).findById(id);
        assertEquals(client.getId(), retrievedClient.getId());
    }
}
