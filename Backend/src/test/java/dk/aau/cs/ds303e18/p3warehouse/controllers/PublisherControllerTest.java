package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestPublisherModel;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Publisher;
import dk.aau.cs.ds303e18.p3warehouse.models.users.User;
import dk.aau.cs.ds303e18.p3warehouse.models.users.UserType;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ProductRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.PublisherRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static dk.aau.cs.ds303e18.p3warehouse.models.DummyProduct.makeDummyProduct;
import static dk.aau.cs.ds303e18.p3warehouse.models.DummyPublisher.makeDummyPublisher;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PublisherControllerTest {

    @Autowired
    PublisherController publisherController;

    @Autowired
    PublisherRepository publisherRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;

    @Before
    public void start() {
        publisherRepository.deleteAll();
        userRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void testFindPublisherById() {
        Publisher publisher = makeDummyPublisher(0, new ObjectId());
        publisherRepository.save(publisher);

        assertEquals(publisher.getUserName(), publisherController.findById(publisher.getHexId()).get().getUserName());
    }

    @Test
    public void testNewPublisher() {
        Publisher publisher = makeDummyPublisher(0, new ObjectId());
        RestPublisherModel restPublisherModel = new RestPublisherModel();

        BeanUtils.copyProperties(publisher, restPublisherModel);
        publisherController.newPublisher(restPublisherModel);

        assertEquals(1, publisherRepository.findAll().size());
    }

    @Test
    public void testUpdatePublisher() {
        Publisher publisher = makeDummyPublisher(0, new ObjectId());
        RestPublisherModel restPublisherModel = new RestPublisherModel();
        BeanUtils.copyProperties(publisher, restPublisherModel);

        publisherRepository.save(publisher);
        userRepository.save(publisher);
        restPublisherModel.setUserName("Rho Aias");
        publisherController.update(publisher.getHexId(), restPublisherModel);

        Assert.assertTrue(restPublisherModel.getUserName().equals(
                publisherRepository.findById(publisher.getHexId()).get().getUserName()));
    }

    @Test
    public void testDeletePublisherById() {
        Publisher publisher = makeDummyPublisher(0, new ObjectId());
        publisherRepository.save(publisher);
        userRepository.save(publisher);

        publisherController.delete(publisher.getHexId());
        assertEquals(0, publisherRepository.findAll().size());
    }

    @Test
    public void testFindPublishersProducts(){
        Publisher publisher = makeDummyPublisher(0, new ObjectId());
        for(int i = 0; i < 5; ++i){
            Product product = makeDummyProduct(i, publisher);
            publisher.addProduct(product);
            productRepository.save(product);
        }
        publisherRepository.save(publisher);

        assertEquals(5, publisherController.findPublisherProducts(publisher.getHexId()).toArray().length);
    }
}
