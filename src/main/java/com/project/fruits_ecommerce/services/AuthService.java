package com.project.fruits_ecommerce.services;

import com.project.fruits_ecommerce.dto.RegisterRequest;
import com.project.fruits_ecommerce.entities.Role;
import com.project.fruits_ecommerce.entities.User;
import com.project.fruits_ecommerce.repository.RoleRepository;
import com.project.fruits_ecommerce.repository.UserRepository;
import com.project.fruits_ecommerce.securityConfig.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RoleRepository roleRepository;


    public String register(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        Optional<Role> roleOptional = roleRepository.findByRoleName(registerRequest.getRoleName());
        if (roleOptional.isPresent()){
            Role role = roleOptional.get();
            user.setRoles(Collections.singleton(role));
        }else {
            throw new RuntimeException("Invalid role name provided");
        }
        userRepository.save(user);
        return "User registered successfully!";
    }
    public String login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (passwordEncoder.matches(password, user.getPassword())) {
            String token = jwtUtil.generateToken(username);
            System.out.println("Generated Token: " + token);
            return jwtUtil.generateToken(username);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRoles().stream()
                            .map(role -> role.getRoleName().name())
                            .toArray(String[]::new))
                    .build();
        } else {
            throw new RuntimeException("User not found");
        }
    }
}