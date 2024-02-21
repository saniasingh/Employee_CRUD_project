package com.employees.crud.employees_crud.controller;

import com.employees.crud.employees_crud.domain.User;
import com.employees.crud.employees_crud.model.ApiResponse;
import com.employees.crud.employees_crud.model.AuthResponse;
import com.employees.crud.employees_crud.model.LoginRequest;
import com.employees.crud.employees_crud.model.RegisterRequest;
import com.employees.crud.employees_crud.service.UserService;
import com.employees.crud.employees_crud.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<ApiResponse<AuthResponse>> registerUser(@RequestBody RegisterRequest registerRequest) {
        try {
            if (userService.getUserByUsername(registerRequest.getUsername()) != null) {
                ApiResponse<AuthResponse> apiResponse = ApiResponse.error("user with same username already exists!!", 500);
                return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));
            }
            final String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

            final User user = userService.registerUser(registerRequest.getUsername(), encodedPassword, registerRequest.getRoles());
            final User newUser = User.builder().username(user.getUsername()).id(user.getId()).roles(user.getRoles()).build();
            final ApiResponse<AuthResponse> apiResponse = ApiResponse.success("User added successfully!!", AuthResponse.builder().user(newUser).token(jwtUtil.generateToken(newUser)).build(), 200);
            return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));
        } catch (Exception e) {
            ApiResponse<AuthResponse> apiResponse = ApiResponse.error(e.getLocalizedMessage(), 500);
            return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));
        }

    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest loginRequest) {
        try {
            final User user = userService.getUserByUsername(loginRequest.getUsername());

            ApiResponse<AuthResponse> apiResponse;
            if (user != null) {
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword(), user.getAuthorities());
                final Authentication authentication = authenticationManager.authenticate(token);
                if (authentication.isAuthenticated()) {
                    final User newUser = User.builder().username(user.getUsername()).id(user.getId()).roles(user.getRoles()).build();
                    apiResponse = ApiResponse.success("Logged in successfully!!", AuthResponse.builder().user(newUser).token(jwtUtil.generateToken(user)).build(), 200);
                } else {
                    apiResponse = ApiResponse.error("Authentication failed", 403);
                }

            } else {
                apiResponse = ApiResponse.error("user does not exist!!", 404);
            }
            return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));

        } catch (DisabledException e) {
            ApiResponse<AuthResponse> apiResponse = ApiResponse.error("user does not exist!!", 404);
            return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));
        } catch (BadCredentialsException e) {
            ApiResponse<AuthResponse> apiResponse = ApiResponse.error("Invalid credentials!!", 404);
            return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getCode()));
        }
    }
}
