package org.fasttrackit.motocamp.web;

import org.fasttrackit.motocamp.domain.User;
import org.fasttrackit.motocamp.service.UserService;
import org.fasttrackit.motocamp.transfer.user.CreateUser;
import org.fasttrackit.motocamp.transfer.user.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/newsfeed/user")
public class ManageUserController {

    private final UserService userService;

    @Autowired
    public ManageUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        User user = userService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping("/")
    public ResponseEntity<UserResponse> getUserByUsername( String request) {
        UserResponse userByUsername = userService.getUserByUsername(request);
        return new ResponseEntity<>(userByUsername, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody CreateUser request) {
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
