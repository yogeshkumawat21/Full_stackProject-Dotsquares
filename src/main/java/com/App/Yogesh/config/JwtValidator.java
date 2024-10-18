package com.App.Yogesh.config;

import com.App.Yogesh.Dto.UserDetailsDto;
import com.App.Yogesh.Dto.UserDto;
import com.App.Yogesh.Models.User;
import com.App.Yogesh.Repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserContext userContext; // Inject UserContext
    @Autowired
    private UserRepository userRepository; // Inject UserRepository

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = request.getHeader(jwtConstant.JWT_HEADER);
            if (jwt != null && JwtProvider.validateJwtToken(jwt.substring(7))) {
                jwt = jwt.substring(7);
                String email = JwtProvider.getEmailFromJwtToken(jwt);
                List<GrantedAuthority> authorities = JwtProvider.getAuthoritiesFromJwtToken(jwt);
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                // Fetch user details from repository
                User user = userRepository.findByEmail(email);
                if (user != null) {
                    // Create UserDetailsDto and set it in UserContext
                    UserDetailsDto userDto = new UserDetailsDto();
                    userDto.setEmail(user.getEmail());
                    userDto.setFirstName(user.getFirstName());
                    userDto.setLastName(user.getLastName());

                    userContext.setCurrentUser(userDto);
                    System.out.println(userDto);
                }
                else
                {
                    throw new Exception("Invalid User");
                }
            } else {
                System.out.println("Invalid JWT Token or not present");
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            userContext.clear(); // Clear the context after the request is processed
        }
    }
}
