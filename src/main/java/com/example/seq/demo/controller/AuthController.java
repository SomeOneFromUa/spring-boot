package com.example.seq.demo.controller;

import com.example.seq.demo.dao.UserDao;
import com.example.seq.demo.dto.request.LoginRequest;
import com.example.seq.demo.dto.request.RegisterRequest;
import com.example.seq.demo.dto.response.BasicResponse;
import com.example.seq.demo.entity.Status;
import com.example.seq.demo.entity.User;
import com.example.seq.demo.security.jwt.JwtProvider;
import com.example.seq.demo.service.inplementation.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@Slf4j
@CrossOrigin()
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public BasicResponse addUser(@RequestBody @Valid RegisterRequest registerRequest) {
        return this.authService.registerUser(registerRequest);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> loginAuth(@RequestBody LoginRequest loginRequest) {
        return this.authService.loginUser(loginRequest);
    }

    @PostMapping("/forgorPassword")
    @ResponseStatus(HttpStatus.OK)
    public BasicResponse forgotPasswordRequest(@RequestParam String email) {
        return this.authService.forgotRequest(email);
    }

// TODO: 15.10.2020 make forget password mapping

}
