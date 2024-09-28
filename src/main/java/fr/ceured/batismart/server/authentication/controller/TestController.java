package fr.ceured.batismart.server.authentication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @GetMapping("/protected")
    public String protectedMethod() {
        return "protected";
    }

}
