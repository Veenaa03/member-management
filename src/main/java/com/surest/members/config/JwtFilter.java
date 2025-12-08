package com.surest.members.config;

import com.surest.members.service.JwtService;
import com.surest.members.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import java.io.IOException;

public class JwtFilter extends GenericFilter {
    private final JwtService jwtService;
    private final UserService userService;

    public JwtFilter(JwtService jwtService, UserService userService){
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                String username = jwtService.extractUsername(token);
                UserDetails userDetails = userService.loadUserByUsername(username);
                if (jwtService.isTokenValid(token, userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (Exception e) {
                // invalid token -> proceed without setting security context (will be unauthorized)
            }
        }
        chain.doFilter(req, res);
    }
}
