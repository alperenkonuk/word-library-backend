package com.wordlibrary.controller;

import com.wordlibrary.entity.User;
import com.wordlibrary.service.implementations.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;


}
