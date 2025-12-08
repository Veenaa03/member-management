package com.surest.members.controller;

import com.surest.members.dto.*;
import com.surest.members.service.JwtService;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import com.surest.members.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthController(AuthenticationManager authManager, JwtService jwtService, UserService userService){
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest req){
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        } catch (AuthenticationException e){
            throw new BadCredentialsException("Invalid credentials");
        }
        UserDetails ud = userService.loadUserByUsername(req.getUsername());
        String token = jwtService.generateToken(ud.getUsername());
        return new AuthResponse(token);
    }
}
