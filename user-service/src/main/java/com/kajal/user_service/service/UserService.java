package com.kajal.user_service.service;

import com.kajal.user_service.dto.UserLoginRequest;
import com.kajal.user_service.dto.UserRegisterRequest;
import com.kajal.user_service.entity.User;
import com.kajal.user_service.entity.UserRole;
import com.kajal.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public String registerUser(UserRegisterRequest userRegisterRequest) {
        try {
            if (userRepository.existsByUserName(userRegisterRequest.getUserName())) {
                throw new RuntimeException("Username already exists");
            }
            if (userRepository.existsByEmail(userRegisterRequest.getEmail())) {
                throw new RuntimeException("Email already exists");
            }

            User user = new User(
                    userRegisterRequest.getEmail(),
                    userRegisterRequest.getUserName(),
                    encoder.encode(userRegisterRequest.getPassword()),
                    UserRole.USER
            );
            User createdUser = userRepository.save(user);

            return authService.generateToken(createdUser.getUserName(), createdUser.getId());
        } catch (Exception e) {
            throw e;
        }
    }

    public String loginUser(UserLoginRequest userLoginRequest) {
        try {
            String userName = userLoginRequest.getUserName();
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(userLoginRequest.getUserName(), userLoginRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                User user = userRepository.findByUserName(userName);
                return authService.generateToken(user.getUserName(), user.getId());
            } else {
                throw new RuntimeException("Authentication failed");
            }
        } catch (BadCredentialsException e){
            throw new RuntimeException("Bad Creds", e);
        } catch (RuntimeException e) {
            throw new RuntimeException("Invalid username or password", e);
        }
    }
}
