package com.exec.controller;

import com.exec.service.UserService;

import java.util.*;

import com.exec.model.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/user")
@RestController
public class UserController {

    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addUser")
    public ResponseEntity<Object> addUser(@RequestBody Map<String, String> body) {
        User new_user = new User(body.get("roll_no"), body.get("name"), body.get("email"));
        userService.addUser(new_user);
        return ResponseEntity.status(HttpStatus.CREATED).build(); 
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
