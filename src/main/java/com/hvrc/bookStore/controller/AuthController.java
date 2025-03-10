package com.hvrc.bookStore.controller;

import com.hvrc.bookStore.jwt.JwtUtil;
import com.hvrc.bookStore.entity.User;
import com.hvrc.bookStore.security.MyUserdetailService;
import com.hvrc.bookStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserdetailService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;
        @PostMapping("/signup")
        public ResponseEntity<String> signup(@RequestBody User user) {
            try {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setRole("USER");
                userService.save(user);
                return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody User user) {
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
                String jwt=jwtUtil.generateToken(user.getUsername());

                return new ResponseEntity<>(jwt, HttpStatus.OK);
            } catch (BadCredentialsException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "Invalid username or password"));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Collections.singletonMap("error", "An unexpected error occurred"));
            }
        }
}
