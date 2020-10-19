package com.example.seq.demo.dao;

import com.example.seq.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.cdi.JpaRepositoryExtension;

import java.util.List;

public interface UserDao extends JpaRepository<User, Long> {
    User getUserByLogin(String login);
    boolean existsUserByEmail(String email);
    boolean existsUserByLogin(String login);
    User findByLogin(String login);
    User findByEmail(String email);
    @Query("select u from User u join fetch u.roles r where r.name=:name")
    List<User> findUsersByRole();

}
