package com.example.seq.demo.controller;

import com.example.seq.demo.dao.RoleDao;
import com.example.seq.demo.dao.UserDao;
import com.example.seq.demo.entity.Role;
import com.example.seq.demo.entity.Status;
import com.example.seq.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class adminController {

    private RoleDao roleDao;
    private UserDao userDao;
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public adminController(RoleDao roleDao, UserDao userDao, PasswordEncoder bCryptPasswordEncoder) {
        this.roleDao = roleDao;
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/roles")
    @ResponseStatus(HttpStatus.OK)
    public List<Role> getRoles(){
        return this.roleDao.findAll();
    }

    @PostMapping("/roles/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    public Role addRole(@PathVariable String name){
        Role role = new Role();
        role.setName(name);
        role.setStatus(Status.ACTIVE);
        role.setCreated(LocalDateTime.now());
        role.setUpdated(LocalDateTime.now());
        return this.roleDao.save(role);
    }

    @DeleteMapping("/roles/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@PathVariable String name){
        Role byName = this.roleDao.findByName(name);
        long id = byName.getId();
        this.roleDao.deleteById(id);
    }

    @GetMapping("/users")
    List<User> getUsers(){
       return this.userDao.findAll();
    }

    @PostMapping("/users/role")
    @ResponseStatus(HttpStatus.CREATED)
    public User setRoleToUser(@RequestParam String name, @RequestParam String role){
        User user = this.userDao.findByLogin(name);
        Role userRole = this.roleDao.findByName(role);
        user.getRoles().add(userRole);
        return this.userDao.save(user);
    }

}
