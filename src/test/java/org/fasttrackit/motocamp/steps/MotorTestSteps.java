package org.fasttrackit.motocamp.steps;

import org.fasttrackit.motocamp.domain.Motorcycle;
import org.fasttrackit.motocamp.domain.User;
import org.fasttrackit.motocamp.service.MotorService;
import org.fasttrackit.motocamp.transfer.motorcycle.CreateMotorcycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

@Component
public class MotorTestSteps {
    @Autowired
    private UserTestSteps userTestSteps;
    @Autowired
    private MotorService motorService;

    public Motorcycle createMotor() {
        User user = userTestSteps.createUser();

        CreateMotorcycle request = new CreateMotorcycle();
        request.setUserId(user.getId());
        request.setModelMotor("Honda");
        request.setUserName("thunder");
        request.setImageUrl("111111");

        Motorcycle motorcycle = motorService.addMotorcycleToUser(request);

        assertThat(motorcycle, notNullValue());
        assertThat(motorcycle.getModelMotor(), is(request.getModelMotor()));
        assertThat(motorcycle.getUserName(), is(request.getUserName()));
        assertThat(motorcycle.getImageUrl(), is(request.getImageUrl()));

        return motorcycle;
    }
}
