package edu.ASpring.devJava2021.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@Controller
public class HelloWorldController {
    //AVEC LA méthode get
    @GetMapping("/hello") //une fois taper /hello appel la méthode
    public String hello(){
        return "index.html";
    }


}
