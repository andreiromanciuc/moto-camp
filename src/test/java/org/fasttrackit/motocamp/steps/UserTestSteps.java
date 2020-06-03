package org.fasttrackit.motocamp.steps;

import org.fasttrackit.motocamp.domain.User;
import org.fasttrackit.motocamp.service.UserService;
import org.fasttrackit.motocamp.transfer.user.CreateUser;
import org.fasttrackit.motocamp.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;

@Component
public class UserTestSteps {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public User createUser() {
        CreateUser request = new CreateUser();
        request.setEmail("andrei.romanciuc@gmail.c");
        request.setUsername("riders");
        request.setPassword("asdg");

        User user = userService.createUser(request);

        assertThat(user, notNullValue());
        assertThat(user.getId(), greaterThan(0L));
        assertThat(user.getEmail(), is(request.getEmail()));
        assertThat(user.getUsername(), is(request.getUsername()));

        return user;
    }
}
