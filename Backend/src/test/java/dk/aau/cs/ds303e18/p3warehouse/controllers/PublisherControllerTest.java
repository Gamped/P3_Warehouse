package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestPublisherModel;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Publisher;
import dk.aau.cs.ds303e18.p3warehouse.repositories.PublisherRepository;
import org.bson.types.ObjectId;
import org.junit.Assert;
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
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PublisherControllerTest {

    @InjectMocks
    PublisherController publisherController;

    @Mock
    PublisherRepository publisherRepository;

    @Test
    public void publisherControllerLoads() throws Exception {
        assertThat(publisherController).isNotNull();
    }

    @Before
    public void start() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindPublisherById() {
        ObjectId id = new ObjectId();
        Publisher publisher = new Publisher(id);

        when(publisherRepository.findById(id)).thenReturn(Optional.of(publisher));

        Optional<Publisher> optPublisher =  publisherController.findById(String.valueOf(id));
        Publisher retrievedPublisher = optPublisher.get();
        verify(publisherRepository).findById(id);
        assertEquals(publisher.getId(), retrievedPublisher.getId());
    }

    @Test
    public void testUpdatePublisher() {
        ObjectId id = new ObjectId();
        Publisher publisher = new Publisher(id);
        RestPublisherModel restPublisher = new RestPublisherModel();
        restPublisher.setCompanyName("aCompany");
        restPublisher.setPublisherName("karen");

        when(publisherRepository.findById(id)).thenReturn(Optional.of(publisher));

        String updatePublisher = publisherController.update(publisher.getHexId(), restPublisher);
        String publisherString = publisher.toString();

        assertNotSame(publisherString, updatePublisher);
    }

    @Test
    public void testDeletePublisherById() {
        ObjectId id = new ObjectId();
        Publisher publisher = new Publisher(id);

        when(publisherRepository.findById(id)).thenReturn(Optional.of(publisher));

        publisherController.delete(publisher.getHexId());
        verify(publisherRepository).deleteById(id);

        List<Publisher> publishers = new ArrayList<>();
        Assert.assertEquals(publishers, publisherRepository.findAll());
    }
}
