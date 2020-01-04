package com.veezy.todoapp.controller;

import com.veezy.todoapp.exception.ResourceNotFoundException;
import com.veezy.todoapp.request.LoginRequest;
import com.veezy.todoapp.response.AuthenticationResponse;
import com.veezy.todoapp.service.TodoUserDetailsService;
import com.veezy.todoapp.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private AuthenticationManager authenticationManager;
    private TodoUserDetailsService todoUserDetailsService;
    private JwtUtil jwtUtil;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    TodoUserDetailsService todoUserDetailsService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.todoUserDetailsService = todoUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                    loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new ResourceNotFoundException("Incorrect username or password");
        }
        final UserDetails userDetails = todoUserDetailsService.loadUserByUsername(loginRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
