package com.example.seq.demo.entity;

import com.example.seq.demo.dto.response.BasicResponse;
import com.example.seq.demo.validator.UniqUsernameAndEmail.UniqUsernameAndEmail;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class User extends BasicEntity {
    @NotBlank
    @Column(nullable = false, unique = true)
    private String login;
    @NotBlank
    private String password;
    @Email
    @Column(nullable = false, unique = true)
    private String email;
    private String tokenForRecoverPassword;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    public User(long id, LocalDateTime created, LocalDateTime updated, Status status, @NotBlank String login, @NotBlank String password, @Email String email) {
        super(id, created, updated, status);
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public List<SimpleGrantedAuthority> getAuthority(){
       return this.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
