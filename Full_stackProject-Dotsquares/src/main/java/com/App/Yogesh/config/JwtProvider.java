package com.App.Yogesh.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.App.Yogesh.config.jwtConstant.SECRET_KEY;

public class JwtProvider {

    // Define the expiration time for JWT tokens
    private static final long EXPIRATION_TIME = 86400000; // 1 day

    public static String generateToken(String email,List<GrantedAuthority> authorities) {
        Claims claims = Jwts.claims().setSubject(email);

        // Store authorities in the claims
        claims.put("authorities", authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Set expiration
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY) // Use the same key for signing
                .compact();
    }

    public static String getEmailFromJwtToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public static List<GrantedAuthority> getAuthoritiesFromJwtToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        List<GrantedAuthority> authorities = new ArrayList<>();
        // Ensure authorities claim is not null before processing
        Object authoritiesClaim = claims.get("authorities");
        if (authoritiesClaim != null) {
            // Check if the claim is a List
            if (authoritiesClaim instanceof List<?>) {
                for (Object authority : (List<?>) authoritiesClaim) {
                    if (authority instanceof String) {
                        authorities.add(new SimpleGrantedAuthority((String) authority));
                    }
                }
            }
        }
        return authorities;
    }


    public static boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
//            System.out.println(token);
            return true; // Token is valid

        } catch (JwtException | IllegalArgumentException e) {
//            System.out.println(token);
            return false; // Token is invalid
        }
    }
}
