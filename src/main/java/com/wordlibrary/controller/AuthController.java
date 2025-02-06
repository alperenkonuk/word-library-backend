package com.wordlibrary.controller;

import com.wordlibrary.entity.User;
import com.wordlibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User body){
        Optional<User> existingUser = userService.getUserByEmail(body.getEmail());
        if(existingUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Email is already taken.");
        }
        User newUser = userService.createUser(body);
        return ResponseEntity.ok(newUser);
    }

}
