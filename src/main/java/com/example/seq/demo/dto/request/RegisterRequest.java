package com.example.seq.demo.dto.request;

import com.example.seq.demo.validator.UniqUsernameAndEmail.UniqUsernameAndEmail;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@UniqUsernameAndEmail
public class RegisterRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
}
