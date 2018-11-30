package dk.aau.cs.ds303e18.p3warehouse.models.users;

import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Publisher;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ClientRepository;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest

public class ClientTest {

    @Autowired
    ClientRepository clientRepository;

    @Test
    public void newclient(){
        ObjectId CId = new ObjectId();
        Client client = new Client(CId);
        assertNotNull(client.getHexId());
    }

    @Test
    public void setNameClient(){
        ObjectId CId = new ObjectId();
        Client client = new Client(CId);
        client.setClientName("TestName");
        assertEquals(client.getClientName(),"TestName");
    }

    @Test
    public void newclientToDB(){
        ObjectId CId = new ObjectId();
        Client client = new Client(CId);
        clientRepository.save(client);
        Client Client = clientRepository.findById(client.getHexId());
        Client retrievedClient = Client;
        Assert.assertEquals(client.getHexId(), retrievedClient.getHexId());
        clientRepository.deleteById(CId);
    }

}