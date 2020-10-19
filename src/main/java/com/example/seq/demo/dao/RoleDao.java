package com.example.seq.demo.dao;

import com.example.seq.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Long> {
    Role findByName(String name);
    void deleteByName(String name);
}
