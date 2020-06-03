package org.fasttrackit.motocamp;

import org.fasttrackit.motocamp.service.EmailServiceImpl;
import org.fasttrackit.motocamp.steps.EmailTestSteps;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailIntegrationTests {
    @Autowired
    private EmailServiceImpl emailService;
    @Autowired
    private EmailTestSteps emailTestSteps;

    @Test
    public void sendEmail_toRealUser_thenMailIsSent() {
        emailTestSteps.sendEmail();
    }
}
