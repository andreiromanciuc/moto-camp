package org.fasttrackit.motocamp.web;

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
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }


    @PostMapping
    public ResponseEntity<User> createUser(@Valid CreateUser request) {

        User user = userService.createUser(request);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        User user = userService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

//    @GetMapping
//    public ResponseEntity<UserResponse> getProfileAfterLogIn(@Valid LogIn request) {
//        if (request.getId() == 0) {
//            UserResponse userResponse = userService.logIn(request);
//            return new ResponseEntity<>(userResponse, HttpStatus.OK);
//        }
//        UserResponse userResponse = userService.getUserResponse(request.getId());
//
//        return new ResponseEntity<>(userResponse, HttpStatus.OK);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @Valid @RequestBody CreateUser request) {
        if (request.getUsername() != null
                && request.getEmail() == null
                && request.getPassword() == null
                && request.getFullName() == null
                && request.getImageUrl() == null) {
            User user = userService.updateUserName(id, request);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else if (request.getUsername() == null
                && request.getEmail() != null
                && request.getPassword() == null
                && request.getFullName() == null
                && request.getImageUrl() == null) {
            User user = userService.updateEmail(id, request);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else if (request.getUsername() == null
                && request.getEmail() == null
                && request.getPassword() != null
                && request.getFullName() == null
                && request.getImageUrl() == null) {
            User user = userService.updatePassword(id, request);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }else if (request.getUsername() == null
                && request.getEmail() == null
                && request.getPassword() == null
                && request.getFullName() == null
                && request.getImageUrl() != null){
            User user = userService.updateImageUrl(id, request);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        User user = userService.updateFullName(id, request);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }


}
