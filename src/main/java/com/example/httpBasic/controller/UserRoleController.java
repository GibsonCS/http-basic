package com.example.httpBasic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserRoleController {

    @PostMapping("/user")
    public ResponseEntity<String> handleTest(Authentication authentication) {
        return new ResponseEntity<>("Accepted", HttpStatus.ACCEPTED);
    }
}
