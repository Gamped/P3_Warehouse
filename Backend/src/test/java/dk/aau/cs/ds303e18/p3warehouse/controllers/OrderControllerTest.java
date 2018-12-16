package dk.aau.cs.ds303e18.p3warehouse.controllers;


import com.fasterxml.jackson.databind.util.BeanUtil;
import dk.aau.cs.ds303e18.p3warehouse.CustomException.InvalidQuantityException;
import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestOrderModel;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Publisher;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import dk.aau.cs.ds303e18.p3warehouse.repositories.*;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static dk.aau.cs.ds303e18.p3warehouse.models.DummyOrder.makeDummyOrder;
import static dk.aau.cs.ds303e18.p3warehouse.models.DummyProduct.makeDummyProduct;
import static dk.aau.cs.ds303e18.p3warehouse.models.DummyPublisher.makeDummyPublisher;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderControllerTest {

    @Autowired
    OrderController orderController;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PublisherRepository publisherRepository;
    @Autowired
    ClientRepository clientRepository;

    @Test
    public void orderControllerLoads() {
        assertThat(orderController).isNotNull();
    }

    @Before
    public void start() {
        orderRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();
        publisherRepository.deleteAll();
        clientRepository.deleteAll();
    }

    @Test
    public void testCreateOrder(){ //Tests if any orders are added to the database at all.
        Publisher publisher = makeDummyPublisher(0, new ObjectId());
        Order order = makeDummyOrder(0, publisher);
        publisher.addOrder(order);


        Product product = makeDummyProduct(10, publisher);
        productRepository.save(product);
        publisher.addProduct(product);
        publisherRepository.save(publisher);

        try {
            order.withNewOrderLine(product, 2);
        } catch (InvalidQuantityException e) {
            e.printStackTrace();
        }

        RestOrderModel restOrderModel = new RestOrderModel();
        BeanUtils.copyProperties(order, restOrderModel);

        orderController.createOrder(publisher.getHexId(), publisher.getUserType().name(), restOrderModel);

        assertEquals(1, orderRepository.findAll().size());
    }

    @Test
    public void testCreateOrderNoProduct() {
        Publisher publisher = makeDummyPublisher(0, new ObjectId());
        Order order = makeDummyOrder(0, publisher);
        publisher.addOrder(order);
        publisherRepository.save(publisher);

        RestOrderModel restOrderModel = new RestOrderModel();
        BeanUtils.copyProperties(order, restOrderModel);

        orderController.createOrder(publisher.getHexId(), publisher.getUserType().name(), restOrderModel);

        assertEquals(0, orderRepository.findAll().size());
    }

    @Test
    public void testCreateOrderNoPublisher(){ //Tests if any orders are added to the database at all.
        Publisher publisher = makeDummyPublisher(0, new ObjectId());
        Order order = makeDummyOrder(0, publisher);
        publisher.addOrder(order);


        Product product = makeDummyProduct(10, publisher);
        productRepository.save(product);

        try {
            order.withNewOrderLine(product, 2);
        } catch (InvalidQuantityException e) {
            e.printStackTrace();
        }

        RestOrderModel restOrderModel = new RestOrderModel();
        BeanUtils.copyProperties(order, restOrderModel);

        orderController.createOrder(publisher.getHexId(), publisher.getUserType().name(), restOrderModel);

        assertEquals(0, orderRepository.findAll().size());
    }

    @Test
    public void testFindAllOrders() {
        ObjectId id = new ObjectId();
        ObjectId objectId = new ObjectId();
        ObjectId orderId = new ObjectId();

        Order order = new Order(id);
        Order secondOrder = new Order(objectId);
        Order thirdOrder = new Order(orderId);

        List<Order> orderList = new LinkedList<>();
        orderList.add(order);
        orderList.add(secondOrder);
        orderList.add(thirdOrder);

        when(orderRepository.findAll()).thenReturn(orderList);

        Collection<Order> orders = orderController.findAllOrders();

        verify(orderRepository).findAll();

        assertNotNull(orders);
        assertEquals(3, orders.size());
        assertEquals(orderList, orders);
    }

}
