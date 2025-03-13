package com.example.messenger.controllers;

import com.example.messenger.models.AuthRequest;
import com.example.messenger.models.AuthResponse;
import com.example.messenger.security.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/authenticate")
    public ResponseEntity<String> createAuthenticationToken(@RequestBody AuthRequest authRequest, HttpServletResponse response) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
        } catch (Exception e) {
            throw new Exception("Incorrect username or password",e);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        String jwt = jwtUtil.generateToken(userDetails);
        Cookie cookie = new Cookie("jwt",jwt);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60*60);
        return ResponseEntity.ok("Вход выполнен успешно");
    }
}
