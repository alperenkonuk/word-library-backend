package com.wordlibrary.controller;

import com.wordlibrary.dto.Response;
import com.wordlibrary.entity.User;
import com.wordlibrary.service.implementations.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

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
