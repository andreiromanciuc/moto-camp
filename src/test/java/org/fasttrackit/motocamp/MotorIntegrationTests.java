package org.fasttrackit.motocamp;

import org.fasttrackit.motocamp.domain.Motorcycle;
import org.fasttrackit.motocamp.service.MotorService;
import org.fasttrackit.motocamp.steps.MotorTestSteps;
import org.fasttrackit.motocamp.steps.UserTestSteps;
import org.fasttrackit.motocamp.transfer.motorcycle.CreateMotorcycle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ConcurrentModificationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;

@SpringBootTest
public class MotorIntegrationTests {
    @Autowired
    private MotorService motorService;
    @Autowired
    private UserTestSteps userTestSteps;
    @Autowired
    private MotorTestSteps motorTestSteps;

    @Test
    void createMotor_whenValidRequest_thenReturnMotor() {
        motorTestSteps.createMotor();
    }

    @Test
    void createMotor_withoutUser_thenThrowException() {
        CreateMotorcycle request = new CreateMotorcycle();
        request.setModelMotor("Honda");
        request.setUserName("thunder");
        request.setImageUrl("111111");

        try {
            motorService.addMotorcycleToUser(request);
        } catch (Exception e) {
            assertThat(e, notNullValue());
            assertThat("Unexpected exception type.", e instanceof ConcurrentModificationException);
        }

    }

    @Test
    void updateMotor_whenMotorExist_thenReturnMotor() {
        Motorcycle motor = motorTestSteps.createMotor();

        CreateMotorcycle request = new CreateMotorcycle();
        request.setUserName(motor.getUserName() + "update");
        Motorcycle response = motorService.updateMotorUserName(motor.getId(), request);

        assertThat(response, notNullValue());
        assertThat(response, not(motor.getUserName()));
    }
}


