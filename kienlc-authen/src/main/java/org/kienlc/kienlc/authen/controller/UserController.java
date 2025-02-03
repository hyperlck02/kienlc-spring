package org.kienlc.kienlc.authen.controller;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.kienlc.kienlc.authen.entity.UserEntity;
import org.kienlc.kienlc.authen.model.LoginRequest;
import org.kienlc.kienlc.authen.repository.UserRepository;
import org.kienlc.kienlc.authen.service.CustomUserService;
import org.kienlc.kienlc.authen.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
//@AllArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final CustomUserService customUserService;
    private final UserService userService;

    public UserController(UserRepository userRepository, CustomUserService customUserService, UserService userService) {
        this.userRepository = userRepository;
        this.customUserService = customUserService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserEntity register(@RequestBody UserEntity user) {
        return customUserService.saveUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {

        return new ResponseEntity<>(userService.verify(request), HttpStatus.OK);
    }
}
