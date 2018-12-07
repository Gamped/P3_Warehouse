package dk.aau.cs.ds303e18.p3warehouse.controllers;

/*
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderControllerTest {

    @InjectMocks
    OrderController orderController;

    @Mock
    OrderRepository orderRepository;

    @Test
    public void orderControllerLoads() throws  Exception{
        assertThat(orderController).isNotNull();
    }

    @Before
    public void start() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDeleteOrderById() {
        ObjectId id =  new ObjectId();
        Order order = new Order(id);

        when(orderRepository.findById(id)).thenReturn(Optional.of(order));

        orderController.deleteOrder(order);
        verify(orderRepository).delete(order);

        assertNotNull(orderRepository.findAll());
    }

    @Test
    public void  testUpdateOrder() {
        ObjectId orderId = new ObjectId();
        Order order = new Order(orderId);
        order.setTitle("Running");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Order newOrder = orderController.updateOrder(order);
        System.out.println(order.getId());
        assertNotNull(order);
    }
}*/
