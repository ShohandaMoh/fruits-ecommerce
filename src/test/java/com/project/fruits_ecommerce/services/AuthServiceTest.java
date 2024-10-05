//package com.project.fruits_ecommerce.services;
//
//import com.project.fruits_ecommerce.dto.RegisterRequest;
//import com.project.fruits_ecommerce.entities.Role;
//import com.project.fruits_ecommerce.entities.RoleName;
//import com.project.fruits_ecommerce.entities.User;
//import com.project.fruits_ecommerce.repository.RoleRepository;
//import com.project.fruits_ecommerce.repository.UserRepository;
//import com.project.fruits_ecommerce.securityConfig.JwtUtil;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class AuthServiceTest {
//    @Mock
//    private UserRepository userRepository;
//    @Mock
//    private RoleRepository roleRepository;
//    @Mock
//    private PasswordEncoder passwordEncoder;
//    @Mock
//    private JwtUtil jwtUtil;
//    @InjectMocks
//    private AuthService authService;
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testRegisterSuccess() {
//        // Given
//        RegisterRequest registerRequest = new RegisterRequest();
//        registerRequest.setUsername("testUser");
//        registerRequest.setPassword("password");
//        registerRequest.setRoleName(RoleName.valueOf("CLIENT"));
//        Role role = new Role();
//        role.setRoleName(RoleName.valueOf("CLIENT"));
//        when(roleRepository.findByRoleName(RoleName.valueOf(anyString()))).thenReturn(Optional.of(role));
//        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
//        // When
//        String result = authService.register(registerRequest);
//        // Then
//        verify(userRepository, times(1)).save(any(User.class));
//        assertEquals("User registered successfully!", result);
//    }
//
//    @Test
//    void testRegisterRoleNotFound() {
//        // Given
//        RegisterRequest registerRequest = new RegisterRequest();
//        registerRequest.setUsername("testUser");
//        registerRequest.setPassword("password");
//        registerRequest.setRoleName(RoleName.valueOf("INVALID_ROLE"));
//        when(roleRepository.findByRoleName(RoleName.valueOf(anyString()))).thenReturn(Optional.empty());
//        // When
//        Exception exception = assertThrows(RuntimeException.class, () -> {
//            authService.register(registerRequest);
//        });
//        // Then
//        verify(userRepository, never()).save(any(User.class));
//        assertEquals("Invalid role name provided", exception.getMessage());
//    }
//
//    @Test
//    void testLoginSuccess() {
//        // Given
//        User user = new User();
//        user.setUsername("testUser");
//        user.setPassword("encodedPassword");
//        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
//        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
//        when(jwtUtil.generateToken(anyString())).thenReturn("jwtToken");
//        // When
//        String token = authService.login("testUser", "password");
//        // Then
//        assertEquals("jwtToken", token);
//    }
//
//    @Test
//    void testLoginUserNotFound() {
//        // Given
//        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
//        // When
//        Exception exception = assertThrows(RuntimeException.class, () -> {
//            authService.login("testUser", "password");
//        });
//        // Then
//        assertEquals("User not found", exception.getMessage());
//    }
//
//    @Test
//    void testLoginInvalidCredentials() {
//        // Given
//        User user = new User();
//        user.setUsername("testUser");
//        user.setPassword("encPassword");
//        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
//        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);
//        // When
//        Exception exception = assertThrows(RuntimeException.class, () -> {
//            authService.login("testUser", "wrongPassword");
//        });
//        // Then
//        assertEquals("Invalid credentials", exception.getMessage());
//    }
//}