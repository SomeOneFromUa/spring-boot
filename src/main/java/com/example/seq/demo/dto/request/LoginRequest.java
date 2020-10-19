package com.example.seq.demo.dto.request;


import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
    @NotNull
    private String login;
    @NotNull
    private String password;
}
