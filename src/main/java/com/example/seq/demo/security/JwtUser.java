package com.example.seq.demo.security;

import com.example.seq.demo.entity.Status;
import com.example.seq.demo.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public class JwtUser implements UserDetails {
    private final long id;
    private final String login;
    private final String password;
    private final String email;
    private final List<SimpleGrantedAuthority> authorities;
    private final boolean isEnabled;
    private final LocalDateTime lastUpdated;


    public JwtUser(long id, String login, String password, String email, List<SimpleGrantedAuthority> authorities, boolean isEnabled, LocalDateTime lastUpdated) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.authorities = authorities;
        this.isEnabled = isEnabled;
        this.lastUpdated = lastUpdated;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isEnabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isEnabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isEnabled;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    public static JwtUser create(User user){
        return new JwtUser(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                user.getEmail(),
                user.getAuthority(),
                user.getStatus().equals(Status.ACTIVE),
                user.getUpdated());
    }
}
