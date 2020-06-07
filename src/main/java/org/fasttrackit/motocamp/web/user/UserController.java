package org.fasttrackit.motocamp.web.user;

import org.fasttrackit.motocamp.domain.User;
import org.fasttrackit.motocamp.persistance.UserRepository;
import org.fasttrackit.motocamp.service.UserService;
import org.fasttrackit.motocamp.transfer.user.CreateUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/signup/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<User> createUser(@Valid CreateUser request) {

        User user = userService.createUser(request);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }




}
