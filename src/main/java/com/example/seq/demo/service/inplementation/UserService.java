package com.example.seq.demo.service.inplementation;

import com.example.seq.demo.dao.UserDao;
import com.example.seq.demo.dto.request.ChangePasswordRequest;
import com.example.seq.demo.dto.response.BasicResponse;
import com.example.seq.demo.entity.User;
import com.example.seq.demo.exeption.ChangePasswordException;
import com.example.seq.demo.security.jwt.JwtProvider;
import com.example.seq.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserService implements IUserService {
    private UserDao userDao;
    private PasswordEncoder bCryptPasswordEncoder;
    private JwtProvider jwtProvider;
@Autowired
    public UserService(UserDao userDao, PasswordEncoder bCryptPasswordEncoder, JwtProvider jwtProvider) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public BasicResponse changePassword(ChangePasswordRequest body, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");
        String userName = jwtProvider.getUserName(token);
        User user = this.userDao.getUserByLogin(userName);
        if (bCryptPasswordEncoder.matches(body.getCurrentPassword(), user.getPassword())){
            if (bCryptPasswordEncoder.matches(body.getNewPassword(), user.getPassword())){
                throw new ChangePasswordException("your new password is equal to current");
            }
            user.setPassword(bCryptPasswordEncoder.encode(body.getNewPassword()));
            this.userDao.flush();
            return new BasicResponse(200, "password changed", null);
        } else throw new ChangePasswordException("invalid current password");
    }

    @Override
    public User getInfo(HttpServletRequest request) {
        return this.userDao.findByLogin(jwtProvider.getUserName(request.getHeader("Authorization")));

    }
}
