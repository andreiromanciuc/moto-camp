package org.fasttrackit.motocamp.web.motorcycle;

import org.fasttrackit.motocamp.domain.Motorcycle;
import org.fasttrackit.motocamp.service.MotorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/timeline/motorcycle")
public class MotorcycleTimelineController {

    private final MotorService motorService;

    public MotorcycleTimelineController(MotorService motorService) {
        this.motorService = motorService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Motorcycle> getMotorcycle(@PathVariable long id) {
        Motorcycle motorcycle = motorService.getMotorcycle(id);
        return new ResponseEntity<>(motorcycle, HttpStatus.OK);
    }
}
