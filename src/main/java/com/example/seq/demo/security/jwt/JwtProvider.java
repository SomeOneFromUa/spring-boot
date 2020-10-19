package com.example.seq.demo.security.jwt;

import com.example.seq.demo.entity.Role;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
@Component
public class JwtProvider {
    @Value("${my.project.security.secret.code}")
    private String secret;
    @Value("${my.project.security.validityAccess.nanos}")
    private int validTimeAccess;
    @Value("${my.project.security.validityAccess.nanos}")
    private int validTimeRefresh;
    @Value("${my.project.security.header.jwt}")
    private String authHeader;
    @Value("${my.project.security.header.jwt-refrsh}")
    private String refreshTokenHeader;

    private UserDetailsService userDetailsService;
@Autowired
    public JwtProvider(@Qualifier("jwtUserDetailsService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

@PostConstruct
protected void init(){
    secret = Base64.getEncoder().encodeToString(secret.getBytes());
}
    public String createToken(String login, List<Role> roles){
        Claims claims = Jwts.claims().setSubject(login);
        claims.put("roles", getRoleNames(roles));
        Date now = new Date();
        Date validity = new Date(now.getTime() + validTimeAccess);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

    }
    public String createRefreshToken(String login, List<Role> roles){
        Claims claims = Jwts.claims().setSubject(login);
        claims.put("roles", getRoleNames(roles));
        Date now = new Date();
        Date validity = new Date(now.getTime() + validTimeRefresh);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

    }

    public boolean validateToken(String token){
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException ex){
            throw new JwtValidateException("JWT token is expired or invalid", HttpStatus.UNAUTHORIZED);
        }
    }
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUserName(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserName(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }
    public String resolveToken(HttpServletRequest httpServletRequest){
        return httpServletRequest.getHeader(authHeader);
    }
    public String resolveRefreshToken(HttpServletRequest httpServletRequest){
        return httpServletRequest.getHeader(refreshTokenHeader);
    }
    private List<String> getRoleNames(List<Role> roles) {
        List<String> rolesRes = new ArrayList<>();
        roles.forEach(role -> rolesRes.add(role.getName()));
        return rolesRes;
    }
}
