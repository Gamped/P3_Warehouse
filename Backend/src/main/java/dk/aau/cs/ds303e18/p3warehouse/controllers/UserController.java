package dk.aau.cs.ds303e18.p3warehouse.controllers;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import dk.aau.cs.ds303e18.p3warehouse.repositories.UserRepository;
import dk.aau.cs.ds303e18.p3warehouse.models.users.User;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

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
    Optional<User> findByUserType(@PathVariable String userType) {
        return userRepository.findByUserType(userType);
    }

    @GetMapping("/users/{id}")
    Optional<User> findById(@PathVariable ObjectId id) {
        return userRepository.findById(id);
    }


}
