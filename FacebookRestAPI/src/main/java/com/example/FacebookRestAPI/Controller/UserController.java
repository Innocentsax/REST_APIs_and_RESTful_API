package com.example.FacebookRestAPI.Controller;

import com.example.FacebookRestAPI.Models.UsersModel;
import com.example.FacebookRestAPI.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public ResponseEntity<String> getRegisterPage() {
        return ResponseEntity.ok("Register page retrieved successfully");
    }

    @GetMapping("/login")
    public ResponseEntity<String> getLoginPage() {
        return ResponseEntity.ok("Login page retrieved successfully");
    }



    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UsersModel usersModel) {
        System.out.println("Register request: " + usersModel);

        UsersModel registeredUser = userService.registerUser(usersModel.getLogin(), usersModel.getPassword(), usersModel.getEmail());
        if (registeredUser != null) {
            return ResponseEntity.ok("User registered successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User registration failed");
        }
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsersModel usersModel) {
        System.out.println("Login request: " + usersModel);

        UsersModel authenticated = userService.authenticate(usersModel.getLogin(), usersModel.getPassword());
        if (authenticated != null) {
            return ResponseEntity.ok("User authenticated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }
}
