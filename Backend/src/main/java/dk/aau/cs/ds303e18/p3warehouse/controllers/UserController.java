package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.users.UserType;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ClientRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.EmployeeRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.PublisherRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import dk.aau.cs.ds303e18.p3warehouse.repositories.UserRepository;
import dk.aau.cs.ds303e18.p3warehouse.models.users.User;
import java.util.Optional;
@RequestMapping("/api")
@CrossOrigin
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    PublisherRepository publisherRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/users")
    private Iterable<User> all() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    private User newUser(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }

    @GetMapping("/users/{userName}")
    Optional<User> findByUserName(@PathVariable String userName) {
        return userRepository.findByUserName(userName);
    }

    @GetMapping("/users/{password}")
    Optional<User> findByPassword(@PathVariable String password) {
        return userRepository.findByPassword(password);
    }

    @GetMapping("/users/{userType}")
    Optional<User> findByUserType(@PathVariable UserType userType) {
        return userRepository.findByUserType(userType);
    }

    @GetMapping("/users/{id}")
    Optional<User> findById(@PathVariable ObjectId id) {
        return userRepository.findById(id);
    }

    @GetMapping("/users/{userName}/{password}")
    private User authenticateUser(@PathVariable("userName") String userName, @PathVariable("password") String password){
        User user = userRepository.findByUserName(userName).orElse(null);
        System.out.println(user.toString());

        if(user != null && user.getPassword().equals(password)){

            switch(user.getUserType()){
                case CLIENT:
                    return employeeRepository.findById(user.getId()).get();

                case PUBLISHER:
                    return publisherRepository.findById(user.getId()).get();

                case EMPLOYEE:
                    return employeeRepository.findById(user.getId()).get();
            }
        }
        return null;
    }


}
