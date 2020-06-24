package org.fasttrackit.motocamp.web.user;

import org.fasttrackit.motocamp.domain.User;
import org.fasttrackit.motocamp.service.user.UserService;
import org.fasttrackit.motocamp.transfer.user.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
@RestController
@RequestMapping("/timeline/user")
public class UserTimelineController {

    private final UserService userService;

    public UserTimelineController(UserService userService) {
        this.userService = userService;
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
}
