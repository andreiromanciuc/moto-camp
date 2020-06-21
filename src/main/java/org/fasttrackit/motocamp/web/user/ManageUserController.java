package org.fasttrackit.motocamp.web.user;

import org.fasttrackit.motocamp.domain.User;
import org.fasttrackit.motocamp.service.post.PostService;
import org.fasttrackit.motocamp.service.UserService;
import org.fasttrackit.motocamp.transfer.user.CreateUser;
import org.fasttrackit.motocamp.transfer.user.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
@RestController
@RequestMapping("/newsfeed/user")
public class ManageUserController {

    private final UserService userService;
    private final PostService postService;

    @Autowired
    public ManageUserController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public Long currentUserName(Principal principal) {
        String name = principal.getName();

        UserResponse user = userService.getUserBySession(name);

        return user.getId();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        User user = userService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<UserResponse> getUserByUsername(CreateUser request) {
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
            postService.updateUserPhotoToPost(id, request);

            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        User user = userService.updateFullName(id, request);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
