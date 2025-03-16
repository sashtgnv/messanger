package com.example.messenger.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;

@Controller
public class ErrorController {

    @GetMapping("/error/403")
    public String accessDenied(){
        System.out.println(12345);
        return "403";
    }
}
