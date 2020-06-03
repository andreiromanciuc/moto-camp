package org.fasttrackit.motocamp.service;

import org.fasttrackit.motocamp.domain.User;
import org.fasttrackit.motocamp.exception.ResourceNotFoundException;
import org.fasttrackit.motocamp.persistance.UserRepository;
import org.fasttrackit.motocamp.transfer.user.CreateUser;
import org.fasttrackit.motocamp.transfer.user.UserResponse;
import org.fasttrackit.motocamp.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final EmailServiceImpl emailService;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, EmailServiceImpl emailService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User createUser(CreateUser request) {
        LOGGER.info("Creating user {}", request);

        User user = new User(request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                "RIDER");

        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new ResourceNotFoundException("This username already exist.");
        }

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new ResourceNotFoundException("This email is already in use for our site.");
        }

        emailService.sendSimpleMessage(user.getEmail(), "Account was created", "Hello! \n" +
                "We are MOTORAR team and we congrats you that your account was successfully created! \n" +
                "LET'S RIDE TOGETHER!");

        return userRepository.save(user);
    }

    public UserResponse getUserResponse(long id) {
        LOGGER.info("Retrieving user and show response user {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User " + id + " not found."));

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setUsername(user.getUsername());

        return userResponse;
    }

    public User getUser(long id) {
        LOGGER.info("Retrieving user {}", id);
       return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User " + id + " not found."));
    }


//    public Page<User> getUsers(GetUsersRequest request, Pageable pageable){
//        LOGGER.info("Searching users {}", request);
//        if (request != null) {
//            if (request.getUserPartialName() != null && request.getEmailPartialName() == null
//                    && request.getNamePartialName() == null) {
//                return userRepository.findByUsernameContaining(request.getUserPartialName(), pageable);
//            } else if (request.getUserPartialName() == null && request.getEmailPartialName() != null
//            && request.getNamePartialName() == null) {
//                return userRepository.findByEmailContaining(request.getEmailPartialName(), pageable);
//            }
//        }
//        return userRepository.findAll(pageable);
//    }

    public User updateUser(long id, CreateUser request) {
        LOGGER.info("Updating user {}: {}", request, id);
        User user = getUser(id);

        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUsername(request.getUsername());

        return userRepository.save(user);
    }

    public User updateUserName(long id, CreateUser request) {
        LOGGER.info("Updating username {} to user {}", request, id);
        User user = getUser(id);
        user.setUsername(request.getUsername());

        emailService.sendSimpleMessage(user.getEmail(), "Update username", "Hello! \n" +
                "We are MOTORAR team and we inform you that your username was updated! \n" +
                "New username is " + request.getUsername() + ". " +
                "LET'S RIDE TOGETHER!");

        return userRepository.save(user);
    }

    public User updateEmail(long id, CreateUser request) {
        LOGGER.info("Updating email {} to user {}", request, id);
        User user = getUser(id);


        user.setEmail(request.getEmail());
        return userRepository.save(user);
    }

    public User updatePassword(long id, CreateUser request) {
        LOGGER.info("Updating password {} to user {}", request, id);
        User user = getUser(id);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);
    }

    public User updateImageUrl(long id, CreateUser request) {
        LOGGER.info("Updating image url for user {}", id);
        User user = getUser(id);

        user.setImageUrl(request.getImageUrl());
        return userRepository.save(user);
    }

    public User updateFullName(long id, CreateUser request) {
        LOGGER.info("Updating full name to user {}", id);
        User user = getUser(id);
        user.setFullName(request.getFullName());
        return userRepository.save(user);
    }

    public void deleteUser(long id) {
        LOGGER.info("Deleting user {}", id);
        User user = getUser(id);
        userRepository.delete(user);
    }

}