package org.fasttrackit.motocamp.service;

import org.fasttrackit.motocamp.domain.Motorcycle;
import org.fasttrackit.motocamp.domain.User;
import org.fasttrackit.motocamp.exception.ResourceNotFoundException;
import org.fasttrackit.motocamp.persistance.MotorRepository;
import org.fasttrackit.motocamp.transfer.motorcycle.CreateMotorcycle;
import org.fasttrackit.motocamp.transfer.motorcycle.UpdateMotorcycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class MotorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final MotorRepository motorRepository;
    private final UserService userService;

    @Autowired
    public MotorService(MotorRepository motorRepository, UserService userService) {
        this.motorRepository = motorRepository;
        this.userService = userService;
    }



//    public Motorcycle createMotor(CreateMotorcycle request) {
//        Motorcycle motorcycle = new Motorcycle();
//        motorcycle.setModelMotor(request.getModelMotor());
//        motorcycle.setUserName(request.getUserName());
//        motorcycle.setImageUrl(request.getImageUrl());
//
//        return motorRepository.save(motorcycle);
//    }

    public Motorcycle getMotorcycle(long id) {
        LOGGER.info("Retrieving motorcycle {}", id);
        return motorRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Motorcycle "+id+" not found"));
    }

    public Motorcycle updateMotorcycleModel(long id, UpdateMotorcycle request) {
        LOGGER.info("Updating motorcycle model {}", id);
        Motorcycle motorcycle = getMotorcycle(id);
        motorcycle.setModelMotor(request.getModelMotor());

        return motorRepository.save(motorcycle);
    }

    public Motorcycle updateMotorUserName(long id, UpdateMotorcycle request) {
        LOGGER.info("Updating motorcycle username {}", request);
        Motorcycle motorcycle = getMotorcycle(id);
        motorcycle.setUserName(request.getUserName());
        return motorRepository.save(motorcycle);
    }

    public Motorcycle updateImageUrl(long id, UpdateMotorcycle request) {
        LOGGER.info("Updating motorcycle imageUrl: {}", request);
        Motorcycle motorcycle = getMotorcycle(id);
        motorcycle.setImageUrl(request.getImageUrl());
        return motorRepository.save(motorcycle);
    }

    @Transactional
    public Motorcycle addMotorcycleToUser(CreateMotorcycle request) {
        LOGGER.info("Adding motor to user: {}", request);
        Motorcycle motorcycle = motorRepository.findById(request.getUserId())
                .orElse(new Motorcycle());

        motorcycle.setModelMotor(request.getModelMotor());
        motorcycle.setUserName(request.getUserName());

        if (motorcycle.getUser() == null) {
            User user = userService.getUser(request.getUserId());
            motorcycle.setUser(user);
        }
        return motorRepository.save(motorcycle);
    }

    public void deleteMotorcycle(long id) {
        LOGGER.info("Deleting motorcycle {}", id);

        motorRepository.deleteById(id);
    }
}
