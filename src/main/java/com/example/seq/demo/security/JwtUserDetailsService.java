package com.example.seq.demo.security;

import com.example.seq.demo.dao.UserDao;
import com.example.seq.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {
    private UserDao userDao;
@Autowired
    public JwtUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = this.userDao.findByLogin(login);
        if (user == null){
            throw new UsernameNotFoundException("user with login: " + login + " not found");
        }
        JwtUser jwtUser = JwtUser.create(user);
        log.info("JwtUserDetailsService.loadUserByUsername - new JWT user was created from user with login: " + login);
        return jwtUser;
    }
}
