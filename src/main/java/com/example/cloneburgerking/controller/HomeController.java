package com.example.cloneburgerking.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {


    @GetMapping("/main")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

}