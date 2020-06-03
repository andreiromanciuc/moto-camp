package org.fasttrackit.motocamp.steps;

import org.fasttrackit.motocamp.service.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailTestSteps {
    @Autowired
    private EmailServiceImpl emailService;

    public void sendEmail() {
        emailService.sendSimpleMessage("andrei.romanciuc@gmail.com"
        , "TEST", "This is a test");
    }
}
