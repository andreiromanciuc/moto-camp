package org.fasttrackit.motocamp.service.user;

import org.fasttrackit.motocamp.domain.Motorcycle;
import org.fasttrackit.motocamp.domain.User;
import org.fasttrackit.motocamp.persistance.UserRepository;
import org.fasttrackit.motocamp.service.MotorService;
import org.fasttrackit.motocamp.transfer.user.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SearchedUserProfile {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final MotorService motorService;

    public SearchedUserProfile(UserRepository userRepository, MotorService motorService) {
        this.userRepository = userRepository;
        this.motorService = motorService;
    }


    public UserResponse getUserProfileAfterSearch(String request) {
        LOGGER.info("Retrieving {} user's profile", request);
        User user = userRepository.findByUsername(request);
        Motorcycle motorcycle = motorService.getMotorcycle(user.getId());

        UserResponse userResponse = new UserResponse();
        userResponse.setImageUrl(user.getImageUrl());
        userResponse.setEmail(user.getEmail());
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setMotorModel(motorcycle.getModelMotor());
        userResponse.setMotorUsername(motorcycle.getUserName());
        userResponse.setMotorPhoto(motorcycle.getImageUrl());

        return userResponse;
    }
}
