package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestPublisherModel;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Publisher;
import dk.aau.cs.ds303e18.p3warehouse.models.users.UserType;
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

import java.util.LinkedList;
import java.util.List;
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
    public void testFindAllPublishers() {
        ObjectId id = new ObjectId();
        ObjectId publisherId = new ObjectId();
        Publisher publisher = new Publisher(id);
        Publisher secondPublisher = new Publisher(publisherId);

        publisher.setUserName("billy");
        secondPublisher.setUserName("holly");

        List<Publisher> publisherList = new LinkedList<>();
        publisherList.add(publisher);
        publisherList.add(secondPublisher);

        when(publisherRepository.findAll()).thenReturn(publisherList);

        Iterable<Publisher> publishers = publisherController.findAll();
        verify(publisherRepository).findAll();

        assertEquals(publisherList, publishers);
    }

    @Test
    public void testFindPublisherById() {
        ObjectId id = new ObjectId();
        Publisher publisher = new Publisher(id);

        when(publisherRepository.findById(publisher.getId())).thenReturn(Optional.of(publisher));

        Optional<Publisher> optPublisher = publisherController.findById(String.valueOf(publisher.getHexId()));
        Publisher retrievedPublisher = optPublisher.get();
        verify(publisherRepository).findById(publisher.getId());
        assertEquals(publisher.getId(), retrievedPublisher.getId());
    }

    @Test
    public void testNewPublisher() {
        RestPublisherModel restPublisherModel = new RestPublisherModel();
        restPublisherModel.setUserName("GoMore");

        publisherController.newPublisher(restPublisherModel);
    }

    @Test
    public void testUpdatePublisher() {
        ObjectId id = new ObjectId();
        Publisher publisher = new Publisher(id);
        RestPublisherModel restPublisher = new RestPublisherModel();
        publisher.setUserName("sophia");
        restPublisher.setUserName("kent");

        System.out.println(publisher);
        when(publisherRepository.findById(publisher.getId())).thenReturn(Optional.of(publisher));

        String updatePublisher = publisherController.update(publisher.getHexId(), restPublisher);
        verify(publisherRepository).findById(publisher.getId());

        assertEquals("Publisher updated! \n" + publisher.getUserName() + "\n" + publisher.getHexId(),
                updatePublisher);
    }

    @Test
    public void testDeletePublisherById() {
        ObjectId id = new ObjectId();
        Publisher publisher = new Publisher(id);

        when(publisherRepository.findById(publisher.getId())).thenReturn(Optional.of(publisher));

        publisherController.delete(publisher.getHexId());
        verify(publisherRepository).deleteById(publisher.getId());

        assertEquals(0, publisherRepository.findAll().size());
    }

    @Test
    public void testPublisherFindAllProducts() {
        ObjectId id = new ObjectId();
        Publisher publisher = new Publisher(id);
        publisher.setUserType(UserType.PUBLISHER);

        when(publisherRepository.findById(publisher.getId())).thenReturn(Optional.of(publisher));

        Optional<Publisher> optPublisher = publisherController.findPublisherInfoById(
               publisher.getHexId());
        Publisher retrievedPublisher = optPublisher.get();

        verify(publisherRepository).findById(publisher.getId());

        assertEquals(publisher.getUserType(), retrievedPublisher.getUserType());
        assertEquals(publisher.getHexId(), retrievedPublisher.getHexId());
    }
}
