package com.wordlibrary.controller;

import com.wordlibrary.entity.User;
import com.wordlibrary.service.implementations.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User body) {
        Optional<User> existingUser = userService.getUserByEmail(body.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("Email is already taken.");
        }
        User newUser = userService.createUser(body);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User body) {
        String alperen = "alperen";
        System.out.println(body);
        return ResponseEntity.ok().body(alperen);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        String alperen = "alperen";
        System.out.println(alperen);
        return ResponseEntity.ok().body(alperen);

    }

}
