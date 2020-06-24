package org.fasttrackit.motocamp;

import org.fasttrackit.motocamp.domain.User;
import org.fasttrackit.motocamp.exception.ResourceNotFoundException;
import org.fasttrackit.motocamp.service.user.UserService;
import org.fasttrackit.motocamp.steps.UserTestSteps;
import org.fasttrackit.motocamp.transfer.user.CreateUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

@SpringBootTest
public class UserServiceIntegrationTests {


    @Autowired
    private UserService userService;
    @Autowired
    private UserTestSteps userTestSteps;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void createUser_whenValidRequest_thenUserIsCreated() {
        userTestSteps.createUser();
    }

    @Test
    public void createUser_whenMissingEmail_ThenExceptionIsThrown() {
        CreateUser request = new CreateUser();
        request.setUsername("ararandrei");
        request.setPassword(passwordEncoder.encode("123"));

        try {
            userService.createUser(request);
        } catch (Exception e) {
            assertThat(e, notNullValue());
            assertThat("Unexpected exception type.", e instanceof ConstraintViolationException);
        }

    }
    @Test
    public void getUser_whenUserExist_thenReturnUser() {
        User user = userTestSteps.createUser();

        User response = userService.getUser(user.getId());

        assertThat(response, notNullValue());
        assertThat(response.getId(), is(user.getId()));
        assertThat(response.getEmail(), is(user.getEmail()));
        assertThat(response.getUsername(), is(user.getUsername()));

    }

    @Test
    public void getUser_whenUserDoNotExist_thenThrowResourceNotFoundException() {
        Assertions.assertThrows(ResourceNotFoundException.class, ()->userService.getUser(999999));

    }

    @Test
    public void updateUser_whenValidRequest_thenReturnUpdatedUser() {
        User user = userTestSteps.createUser();

        CreateUser request = new CreateUser();
        request.setEmail(user.getEmail() + " updated");
        request.setUsername(user.getUsername() + " updated");
        request.setPassword(user.getPassword()+ " updated");

        User updatedUser = userService.updateUser(user.getId(), request);

        assertThat(updatedUser, notNullValue());
        assertThat(updatedUser.getId(), is(user.getId()));
        assertThat(updatedUser.getEmail(), is(request.getEmail()));
        assertThat(updatedUser.getUsername(), is(request.getUsername()));
        assertThat(updatedUser.getPassword(), is(request.getPassword()));
    }

    @Test
    public void updatePasswordToUser_whenValidRequest_thenUserIsUpdated() {
        User user = userTestSteps.createUser();

        CreateUser request = new CreateUser();
        request.setPassword("asdf");

        User updated = userService.updatePassword(user.getId(), request);

        assertThat(updated, notNullValue());
        assertThat(updated.getPassword(), not(user.getPassword()));
    }

    @Test
    public void deleteUser_whenExistingUser_thenUserDoesNotExist() {
        User user = userTestSteps.createUser();

        userService.deleteUser(user.getId());

    }




}
