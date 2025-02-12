package com.wordlibrary.controller;

import com.wordlibrary.dto.Response;
import com.wordlibrary.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get-all")
    @PreAuthorize("hasAuthority('ADMIN')")   //Right now there is no role feature in the application
    public ResponseEntity<Response> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/user-info")
    public ResponseEntity<Response> getUserInfo() {
        return ResponseEntity.ok(userService.getUserInfo());
    }
}
