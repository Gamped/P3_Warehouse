package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ClientRepository;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
        client.setUserName("ole");
        when(clientRepository.findById(id)).thenReturn(Optional.ofNullable(client));

        Optional<Client> client1 = clientController.findById(id);
        Client clientFromDatabase = client1.get();
        verify(clientRepository).findById(id);

        assertThat(clientFromDatabase.getId(), is(id));


    }
}
