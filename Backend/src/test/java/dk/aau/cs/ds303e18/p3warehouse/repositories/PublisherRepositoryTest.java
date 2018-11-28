package dk.aau.cs.ds303e18.p3warehouse.repositories;

import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Publisher;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataMongoTest
public class PublisherRepositoryTest {

    @Autowired
    PublisherRepository publisherRepository;
    ClientRepository clientRepository;

    ObjectId idP = new ObjectId();
    ObjectId idC = new ObjectId();
    Publisher publisher = new Publisher(idP);
    Client client = new Client(idC);

    @Test
    public void savePublisher(){
        publisherRepository.save(publisher);
        System.out.println(publisher.getHexId());
        Optional<Publisher> optionalPublisher = publisherRepository.findById(publisher.getHexId());
        Publisher retrievedPublisher = optionalPublisher.get();
        Assert.assertEquals(publisher.getHexId(), retrievedPublisher.getHexId());
    }
    @Test
    public void savePublisherClient(){
        publisherRepository.save(publisher);
        clientRepository.save(client);
        System.out.println(publisher.getHexId());
        System.out.println(client.getHexId());
        Optional<Publisher> optionalPublisher = publisherRepository.findById(publisher.getHexId());
        Optional<Client>optionalClient = clientRepository.findById(client.getHexId());
        Publisher retrievedPublisher = optionalPublisher.get();
        Assert.assertEquals(publisher.getHexId(), retrievedPublisher.getHexId());
    }

}
