package com.wordlibrary.service.implementations;


import com.wordlibrary.dto.LoginRequest;
import com.wordlibrary.dto.RefreshRequest;
import com.wordlibrary.dto.Response;
import com.wordlibrary.dto.UserDto;
import com.wordlibrary.entity.User;
import com.wordlibrary.exception.InvalidCredentialsException;
import com.wordlibrary.exception.NotFoundException;
import com.wordlibrary.mapper.EntityDtoMapper;
import com.wordlibrary.repository.UserRepository;
import com.wordlibrary.security.JwtUtils;
import com.wordlibrary.service.interfaces.UserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
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
        Optional<User> existingUser = userRepository.findByEmail(registrationRequest.getEmail());

        if(existingUser.isPresent()) {
            return Response.builder()
                    .status(400)
                    .message("This email is already in use.")
                    .build();
        }

        existingUser = userRepository.findByUsername(registrationRequest.getUsername());

        if(existingUser.isPresent()) {
            return Response.builder()
                    .status(400)
                    .message("This username is already taken.")
                    .build();
        }

        if(registrationRequest.getPassword().length() < 8) {
            return Response.builder()
                    .status(400)
                    .message("Password must be at least 8 characters.")
                    .build();
        }

        User user = User.builder()
                .username(registrationRequest.getUsername())
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .streak(1)
                .lastActiveDate(LocalDate.now())
                .build();

        User savedUser = userRepository.save(user);
        String accessToken = jwtUtils.generateAccessToken(savedUser);
        String refreshToken = jwtUtils.generateRefreshToken(savedUser);

        UserDto userDto = entityDtoMapper.mapUserToDtoBasic(savedUser);

        return Response.builder()
                .status(200)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
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
            throw new InvalidCredentialsException("Password is wrong");
        }

        updateStreak(loginUser);

        String accessToken = jwtUtils.generateAccessToken(loginUser);
        String refreshToken = jwtUtils.generateRefreshToken(loginUser);
        UserDto userDto = entityDtoMapper.mapUserToDtoBasic(loginUser);


        return Response.builder()
                .status(200)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(userDto)
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
    public Response refreshToken(RefreshRequest request) {
        Claims claims = jwtUtils.extractClaims(request.getToken());
        String refreshToken = jwtUtils.generateRefreshToken(claims.getSubject());

        return Response.builder()
                .status(200)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void updateStreak(User user) {
        LocalDate today = LocalDate.now();

        LocalDate lastActiveDate = user.getLastActiveDate();

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
