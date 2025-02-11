package com.wordlibrary.service.interfaces;

import com.wordlibrary.dto.LoginRequest;
import com.wordlibrary.dto.Response;
import com.wordlibrary.dto.UserDto;
import com.wordlibrary.entity.User;

public interface UserService {
    Response registerUser(UserDto registrationRequest);

    Response loginUser(LoginRequest loginRequest);

    User getLoginUser();

    Response getUserInfo();

    Response getAllUsers();

}
