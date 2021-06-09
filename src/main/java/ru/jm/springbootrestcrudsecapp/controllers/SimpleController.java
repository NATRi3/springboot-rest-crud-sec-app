package ru.jm.springbootrestcrudsecapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SimpleController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping
    public String mainPage() {
        return "index";
    }
}
