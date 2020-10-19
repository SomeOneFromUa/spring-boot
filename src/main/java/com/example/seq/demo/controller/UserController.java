package com.example.seq.demo.controller;

import com.example.seq.demo.dao.UserDao;
import com.example.seq.demo.dto.request.ChangePasswordRequest;
import com.example.seq.demo.dto.response.BasicResponse;
import com.example.seq.demo.entity.User;
import com.example.seq.demo.exeption.ChangePasswordException;
import com.example.seq.demo.security.jwt.JwtProvider;
import com.example.seq.demo.service.inplementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private UserService userService;
@Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    public BasicResponse changePassword(@RequestBody ChangePasswordRequest body, HttpServletRequest httpServletRequest){
       return this.userService.changePassword(body, httpServletRequest);
    }


    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    public User getInfo(HttpServletRequest request){
    return this.userService.getInfo(request);
    }
}
