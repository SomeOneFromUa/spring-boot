package com.example.seq.demo.service.inplementation;

import com.example.seq.demo.dao.RoleDao;
import com.example.seq.demo.dao.UserDao;
import com.example.seq.demo.dto.request.LoginRequest;
import com.example.seq.demo.dto.request.RegisterRequest;
import com.example.seq.demo.dto.response.BasicResponse;
import com.example.seq.demo.entity.Role;
import com.example.seq.demo.entity.Status;
import com.example.seq.demo.entity.User;
import com.example.seq.demo.security.jwt.JwtProvider;
import com.example.seq.demo.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private PasswordEncoder bCryptPasswordEncoder;
    private UserDao userDao;
    private JwtProvider jwtProvider;
    private RoleDao roleDao;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, PasswordEncoder bCryptPasswordEncoder, UserDao userDao, JwtProvider jwtProvider, RoleDao roleDao) {
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDao = userDao;
        this.jwtProvider = jwtProvider;
        this.roleDao = roleDao;
    }

    @Override
    public BasicResponse registerUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setLogin(registerRequest.getUsername());
        user.setPassword(this.bCryptPasswordEncoder.encode(registerRequest.getPassword()));
        user.setStatus(Status.ACTIVE);
        user.setCreated(LocalDateTime.now());
        user.setUpdated(LocalDateTime.now());
        Role user_role = this.roleDao.findByName("USER_ROLE");
        user.getRoles().add(user_role);
        this.userDao.save(user);
        return new BasicResponse(HttpStatus.CREATED.value(), "user was registered", null);
    }

    @Override
    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));
            User user = userDao.findByLogin(loginRequest.getLogin());
            if (user == null) {
                throw new UsernameNotFoundException("user not exist");
            }
            String token = jwtProvider.createToken(loginRequest.getLogin(), user.getRoles());
            return ResponseEntity.ok().header("Authorization", token).body(user);
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new BasicResponse(HttpStatus.FORBIDDEN.value(), "wrong login or password", "invalid input data"));
        }
    }

    @Override
    public BasicResponse forgotRequest(String email) {
//        User byEmail = this.userDao.findByEmail(email);
//        if (byEmail == null) {
//            throw new UsernameNotFoundException("user with email: " + email + " not found");
//        }
//        String token = UUID.randomUUID().toString();
//
//        byEmail.setTokenForRecoverPassword(token);
        return new BasicResponse();
    }
}
