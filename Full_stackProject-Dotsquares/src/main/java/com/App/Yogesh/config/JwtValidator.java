package com.App.Yogesh.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader(jwtConstant.JWT_HEADER);
        System.out.println(jwt);
        if (jwt != null && JwtProvider.validateJwtToken(jwt.substring(7))) {
            jwt=jwt.substring(7);
            String email = JwtProvider.getEmailFromJwtToken(jwt);
            List<GrantedAuthority> authorities = JwtProvider.getAuthoritiesFromJwtToken(jwt);
            Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            System.out.println("Invalid JWT Token or not present"); // Debug logging
            System.out.println(jwt);
        }

        filterChain.doFilter(request, response);
    }
}
