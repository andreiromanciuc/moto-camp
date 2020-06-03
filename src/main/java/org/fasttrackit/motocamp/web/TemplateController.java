package org.fasttrackit.motocamp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TemplateController {

    @GetMapping("login")
    public String getLoginView() {
        return "login";
    }
    @GetMapping("newsfeed")
    public String getFeedView() {
        return "newsfeed";
    }
    @GetMapping("signup")
    public String getSignUpView() {
        return "signup";
    }
    @GetMapping("createMotorcycle")
    public String createMotor() {
        return "createMotorcycle";
    }
}
