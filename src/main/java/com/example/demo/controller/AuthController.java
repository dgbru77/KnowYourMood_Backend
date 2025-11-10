package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    UserService userService;

    // 🧩 Register User
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists!");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    // 🔑 Login User
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getEmail(),
                            loginDTO.getPassword()
                    )
            );

            if (authentication.isAuthenticated()) {
                User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow();
                String token = jwtUtil.generateToken(String.valueOf(user.getId()));
                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.badRequest().body("Invalid credentials!");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid email or password!");
        }
    }
    @GetMapping("/me")
    public ResponseEntity<?> getLoggedUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);

        Long userId = Long.parseLong(jwtUtil.extractUsername(token));
        User user = userRepository.findById(userId).orElseThrow();

        return ResponseEntity.ok(user.getUsername());
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAccount(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid token");
        }

        String token = authHeader.substring(7);
        Long userId = Long.parseLong(jwtUtil.extractUsername(token)); // user_id stored in token

        String result = userService.deleteAccount(userId);

        return ResponseEntity.ok(result);
    }
@GetMapping("/auth/test")
public String test() {
    return "Backend OK";
}



}
