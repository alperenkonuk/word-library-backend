package com.wordlibrary.controller;

import com.wordlibrary.dto.LoginRequest;
import com.wordlibrary.dto.RefreshRequest;
import com.wordlibrary.dto.Response;
import com.wordlibrary.dto.UserDto;
import com.wordlibrary.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody UserDto registrationRequest) {
        return ResponseEntity.ok(userService.registerUser(registrationRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<Response> loginRequest(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<Response> generateAccessToken(@RequestHeader String authorization) {
        return ResponseEntity.ok(userService.refreshToken(authorization));

    }
}
