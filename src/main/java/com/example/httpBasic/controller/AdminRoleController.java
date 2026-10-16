package com.example.httpBasic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AdminRoleController {

    @GetMapping("/admin")
    public ResponseEntity<String> handleAdminRoleController() {
        return new ResponseEntity<>("accepted", HttpStatus.ACCEPTED);
    }
}
