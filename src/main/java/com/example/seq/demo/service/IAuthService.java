package com.example.seq.demo.service;

import com.example.seq.demo.dto.request.LoginRequest;
import com.example.seq.demo.dto.request.RegisterRequest;
import com.example.seq.demo.dto.response.BasicResponse;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    BasicResponse registerUser(RegisterRequest registerRequest);
    ResponseEntity<?> loginUser(LoginRequest loginRequest);

    BasicResponse forgotRequest(String email);
}
