package com.example.cloneburgerking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/health")
    public ResponseEntity gethealthcheck(){
        return ResponseEntity.ok("health check 성-공");
    }
}
