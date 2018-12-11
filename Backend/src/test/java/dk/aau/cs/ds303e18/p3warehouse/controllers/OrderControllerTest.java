package dk.aau.cs.ds303e18.p3warehouse.controllers;


import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ClientRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.OrderRepository;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderControllerTest {

    @InjectMocks
    OrderController orderController;

    @Mock
    OrderRepository orderRepository;

    @Mock
    ClientRepository clientRepository;

    @Test
    public void orderControllerLoads() {
        assertThat(orderController).isNotNull();
    }

    @Before
    public void start() {
        MockitoAnnotations.initMocks(this);
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
