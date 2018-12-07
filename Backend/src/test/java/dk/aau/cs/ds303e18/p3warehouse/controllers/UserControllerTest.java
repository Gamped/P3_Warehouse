package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserRepository userRepository;

    @Test
    public void userControllerLoads() throws Exception {
        assertThat(userController).isNotNull();
    }

    @Before
    public void start() {
        MockitoAnnotations.initMocks(this);
    }
}
/*
    @Test
    public void testFindUserById() {
        ObjectId id = new ObjectId();
        Client client = new Client(id);
        client.setUserType(UserType.CLIENT);
        client.setUserName("Ole");
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setEmail("Ole@ale.ol");

        userRepository.save(client);

        when(userRepository.findById(id)).thenReturn(Optional.of(client));

        Optional<User> optUser = userController.findById(id);
        User retrievedUser = optUser.get();
        assertEquals(client.getId(), retrievedUser.getId());

    }

    @Test
    public void testFindUserById02() {
        ObjectId id = new ObjectId();
        Employee employee = new Employee(id);
        employee.setUserType(UserType.EMPLOYEE);
        employee.setUserName("Peter123");
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setEmail("123@ale.ol");

        userRepository.save(employee);

        when(userRepository.findById(id)).thenReturn(Optional.of(employee));

        Optional<User> optUser = userController.findById(id);
        User retrievedUser = optUser.get();
        assertEquals(employee.getId(), retrievedUser.getId());
    }

    @Test
    public void testFindUserById03() {
        ObjectId id = new ObjectId();
        Publisher publisher = new Publisher(id);
        publisher.setUserType(UserType.PUBLISHER);
        publisher.setUserName("ifrus");
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setEmail("ifrus@ale.ol");
        publisher.setContactInformation(contactInformation);

        userRepository.save(publisher);

        when(userRepository.findById(id)).thenReturn(Optional.of(publisher));

        Optional<User> optUser = userController.findById(id);
        User retrievedUser = optUser.get();
        verify(userRepository).findById(id);
        assertEquals(publisher.getId(), retrievedUser.getId());

    }

    @Test
    public void testFindUserByUserName() {
        ObjectId id = new ObjectId();
        Employee employee = new Employee(id);
        employee.setUserName("dedef");

        when(userRepository.findByUserName(employee.getUserName())).thenReturn(Optional.of(employee));

        Optional<User> optUser = userController.findByUserName(employee.getUserName());
        User retrievedUser = optUser.get();
        assertEquals(employee.getUserName(), retrievedUser.getUserName());
    }

    @Test
    public void testFindUserByPassword() {
        ObjectId id = new ObjectId();
        Publisher publisher = new Publisher(id);
        publisher.setPassword("awde156aDD");

        when(userRepository.findByPassword(publisher.getPassword())).thenReturn(Optional.of(publisher));

        Optional<User> optUser = userController.findByPassword(publisher.getPassword());
        User retrievedUser = optUser.get();
        assertEquals(publisher.getPassword(), retrievedUser.getPassword());
    }

    @Test
    public void testFindUserByUserType() {
        ObjectId id = new ObjectId();
        Client client = new Client(id);
        client.setUserType(UserType.CLIENT);

        when(userRepository.findByUserType(client.getUserType())).thenReturn(Optional.of(client));

        Optional<User> optUser = userController.findByUserType(client.getUserType());
        User retrievedUser = optUser.get();
        assertEquals(client.getUserType(), retrievedUser.getUserType());
    }
}
*/