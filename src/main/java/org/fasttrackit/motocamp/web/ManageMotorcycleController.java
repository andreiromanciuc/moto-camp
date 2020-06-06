package org.fasttrackit.motocamp.web;

import org.fasttrackit.motocamp.domain.Motorcycle;
import org.fasttrackit.motocamp.service.MotorService;
import org.fasttrackit.motocamp.transfer.motorcycle.UpdateMotorcycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/newsfeed/motorcycle")
public class ManageMotorcycleController {

    private final MotorService motorService;

    @Autowired
    public ManageMotorcycleController(MotorService motorService) {
        this.motorService = motorService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Motorcycle> getMotorcycle(@PathVariable long id) {
        Motorcycle motorcycle = motorService.getMotorcycle(id);
        return new ResponseEntity<>(motorcycle, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Motorcycle> updateMotorModel(@PathVariable long id, @RequestBody UpdateMotorcycle request) {
        if (request.getModelMotor() != null
                && request.getUserName() == null
                && request.getImageUrl() == null) {
            Motorcycle motorcycle = motorService.updateMotorcycleModel(id, request);
            return new ResponseEntity<>(motorcycle, HttpStatus.OK);
        } else if (request.getModelMotor() == null
                && request.getUserName() != null
                && request.getImageUrl() == null) {
            Motorcycle motorcycle = motorService.updateMotorUserName(id, request);
            return new ResponseEntity<>(motorcycle, HttpStatus.OK);
        }
        Motorcycle motorcycle = motorService.updateImageUrl(id, request);
        return new ResponseEntity<>(motorcycle, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Motorcycle> deleteMotorcycle(@PathVariable long id) {
        motorService.deleteMotorcycle(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
