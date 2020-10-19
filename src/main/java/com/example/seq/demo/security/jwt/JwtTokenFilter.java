package com.example.seq.demo.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;
@Autowired
    public JwtTokenFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String refreshToken = this.jwtProvider.resolveRefreshToken((HttpServletRequest) servletRequest);
        String token = this.jwtProvider.resolveToken((HttpServletRequest) servletRequest);
        try {
            if (token != null && jwtProvider.validateToken(token)){
                Authentication authentication = jwtProvider.getAuthentication(token);
                if (authentication != null){
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
        }
        } catch (JwtValidateException ex){
                SecurityContextHolder.clearContext();
            ((HttpServletResponse) servletResponse).sendError(ex.getHttpStatus().value(), ex.getMessage());
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
