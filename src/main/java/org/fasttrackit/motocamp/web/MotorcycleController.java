package org.fasttrackit.motocamp.web;

import org.fasttrackit.motocamp.domain.Motorcycle;
import org.fasttrackit.motocamp.service.MotorService;
import org.fasttrackit.motocamp.transfer.motorcycle.CreateMotorcycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/createMotorcycle/motorcycle")
public class MotorcycleController {

    private final MotorService motorService;

    @Autowired
    public MotorcycleController(MotorService motorService) {
        this.motorService = motorService;
    }

    @PostMapping
    public ResponseEntity<Motorcycle> addMotorcycleToUser(@Valid @RequestBody CreateMotorcycle addMotorToUser) {
        Motorcycle motorcycle = motorService.addMotorcycleToUser(addMotorToUser);
        return new ResponseEntity<>(motorcycle, HttpStatus.CREATED);
    }


}
