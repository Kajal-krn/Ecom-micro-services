package com.kajal.user_service.controller;

import com.kajal.user_service.dto.UserLoginRequest;
import com.kajal.user_service.dto.UserRegisterRequest;
import com.kajal.user_service.service.AuthService;
import com.kajal.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterRequest userRegisterRequest){
        try {
            String signedToken = userService.registerUser(userRegisterRequest);
            return new ResponseEntity<>(signedToken, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest userLoginRequest){
        try {
            String signedToken = userService.loginUser(userLoginRequest);
            return new ResponseEntity<>(signedToken,  HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
