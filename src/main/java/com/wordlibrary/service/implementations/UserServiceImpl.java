package com.wordlibrary.service.implementations;


import com.wordlibrary.dto.LoginRequest;
import com.wordlibrary.dto.Response;
import com.wordlibrary.dto.UserDto;
import com.wordlibrary.entity.User;
import com.wordlibrary.exception.InvalidCredentialsException;
import com.wordlibrary.exception.NotFoundException;
import com.wordlibrary.mapper.EntityDtoMapper;
import com.wordlibrary.repository.UserRepository;
import com.wordlibrary.security.JwtUtils;
import com.wordlibrary.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final EntityDtoMapper entityDtoMapper;


    @Override
    public Response registerUser(UserDto registrationRequest) {
        User user = User.builder()
                .username(registrationRequest.getUsername())
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .password(registrationRequest.getPassword())
                .build();

        User savedUser = userRepository.save(user);

        UserDto userDto = entityDtoMapper.mapUserToDtoBasic(savedUser);

        return Response.builder()
                .status(200)
                .message("User added succesfully")
                .user(userDto)
                .build();

    }

    @Override
    public Response loginUser(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());

        if (user.isEmpty()) {
            throw new NotFoundException("Email not found");
        }

        User loginUser = user.get();

        if (!passwordEncoder.matches(loginRequest.getPassword(), loginUser.getPassword())) {
            throw new InvalidCredentialsException("Password doesn't match");
        }
        String token = jwtUtils.generateToken(loginUser);

        return Response.builder()
                .status(200)
                .message("User logged in succesfully")
                .token(token)
                .expirationTime("7 days")
                .build();

    }

    @Override
    public User getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        log.info("User email:{}", email);

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public Response getUserInfo() {
        User user = getLoginUser();
        UserDto userDto = entityDtoMapper.mapUserToDtoBasic(user);

        return Response.builder()
                .status(200)
                .user(userDto)
                .build();
    }

    @Override
    public void updateStreak(User user) {
        LocalDate today = LocalDate.now();

        LocalDate lastActiveDate = userRepository.getLastActiveDateByUserId(user.getId());

        if (lastActiveDate == null || !lastActiveDate.equals(today)) {
            LocalDate yesterday = today.minusDays(1);

            if (yesterday.equals(lastActiveDate)) {
                user.setStreak(user.getStreak() + 1);
            } else {
                user.setStreak(0);
            }

            user.setLastActiveDate(today);
            userRepository.save(user);
        }
    }

    @Override
    public Response getAllUsers() {
        List<UserDto> userList = userRepository.findAll()
                .stream()
                .map(entityDtoMapper::mapUserToDtoBasic)
                .toList();

        return Response.builder()
                .status(200)
                .userList(userList)
                .build();
    }
}
