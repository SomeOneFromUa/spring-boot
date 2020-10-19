package com.example.seq.demo.security.jwt;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

@Getter
public class JwtValidateException extends AuthenticationException {
    private HttpStatus httpStatus;

    public JwtValidateException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }
}
