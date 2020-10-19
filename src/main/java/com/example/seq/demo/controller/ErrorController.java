package com.example.seq.demo.controller;

import com.example.seq.demo.dto.response.BasicResponse;
import com.example.seq.demo.dto.response.ErrorRegisterResponse;
import com.example.seq.demo.exeption.ChangePasswordException;
import com.example.seq.demo.exeption.RegisterException;
import com.example.seq.demo.security.jwt.JwtValidateException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(RegisterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorRegisterResponse handleRegisterException(RegisterException ex){
        return ex.getErrorRegisterResponse();
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BasicResponse handleUsernameNotFoundException(UsernameNotFoundException ex){
        return new BasicResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), "User not found");
    }

    @ExceptionHandler(JwtValidateException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BasicResponse handleJwtValidateException(JwtValidateException ex){
        return new BasicResponse(ex.getHttpStatus().value(), ex.getMessage(), "authorization error");
    }
    @ExceptionHandler(ChangePasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BasicResponse handleChangePasswordException(ChangePasswordException ex){
        return new BasicResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), "change password error");
    }
}
