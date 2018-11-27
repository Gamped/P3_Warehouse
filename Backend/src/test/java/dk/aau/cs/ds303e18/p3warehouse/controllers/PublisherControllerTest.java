package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.users.Publisher;
import dk.aau.cs.ds303e18.p3warehouse.repositories.PublisherRepository;
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

        when(publisherRepository.findById(id)).thenReturn(Optional.ofNullable(publisher));

        Optional<Publisher> optPublisher =  publisherController.findById(String.valueOf(id));
        Publisher retrievedPublisher = optPublisher.get();
        assertEquals(publisher.getId(), retrievedPublisher.getId());
    }
}
