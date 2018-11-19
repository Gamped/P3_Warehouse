package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.users.IUser;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import dk.aau.cs.ds303e18.p3warehouse.repositories.UserRepository;
import dk.aau.cs.ds303e18.p3warehouse.models.users.User;

import java.util.List;

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
    User one(@PathVariable String userName) {
        return userRepository.findById(userName);
    }

    @GetMapping("/users/{password}")
    User one(@PathVariable String password) {
        return userRepository.findById(password);
    }

    @GetMapping("/users/{userType}")
    User one(@PathVariable String userType) {
        return userRepository.findById(userType);
    }

    @GetMapping("/users/{id}")
    User one(@PathVariable ObjectId id) {
        return userRepository.findById(id);
    }

    @PutMapping("/users/{id}")
    User editUser(@RequestBody User newUser, @PathVariable ObjectId id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUserName(newUser.getUserName());
                    user.setPassword(newUser.getPassword());
                    user.setUserType(newUser.getUserType());
                    user.setId(newUser.getId());
                })
                .orElseGet(() -> {
                    newUser.setUserName(userName);
                    newUser.setPassword(password);
                    newUser.setUserType(userType);
                    newUser.setId(id);
                    return userRepository.save(newUser);
                });
    }
}
