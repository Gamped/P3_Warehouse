package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.users.User;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ClientRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.EmployeeRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.PublisherRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping("/api")
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


    @GetMapping("/users/login/{userName}/{password}")
    private User authenticateUser(@PathVariable("userName") String userName, @PathVariable("password") String password){
        User user = userRepository.findByUserName(userName).orElse(null);

        if(user != null && user.getPassword().equals(password)){

            switch(user.getUserType()){
                case CLIENT:
                    return clientRepository.findById(user.getId()).get();

                case PUBLISHER:
                    return publisherRepository.findById(user.getId()).get();

                case EMPLOYEE:
                    return employeeRepository.findById(user.getId()).get();
            }
        }
        return user;
    }


}
