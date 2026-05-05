package com.spendsmart.auth.service;

import com.spendsmart.auth.config.JwtUtil;
import com.spendsmart.auth.dto.LoginRequest;
import com.spendsmart.auth.dto.RegisterRequest;
import com.spendsmart.auth.entity.Role;
import com.spendsmart.auth.entity.User;
import com.spendsmart.auth.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    // ✅ REGISTER TEST
    @Test
    void testRegister_Success() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@mail.com");
        request.setPassword("1234");
        request.setName("Aaruu");

        when(passwordEncoder.encode("1234")).thenReturn("encoded1234");

        User savedUser = new User();
        savedUser.setEmail("test@mail.com");
        savedUser.setName("Aaruu");
        savedUser.setPassword("encoded1234");
        savedUser.setRole(Role.USER);

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = authService.register(request);

        assertNotNull(result);
        assertEquals("test@mail.com", result.getEmail());
        assertEquals(Role.USER, result.getRole());

        verify(userRepository, times(1)).save(any(User.class));
    }

    // ✅ LOGIN SUCCESS
    @Test
    void testLogin_Success() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@mail.com");
        request.setPassword("1234");

        User user = new User();
        user.setEmail("test@mail.com");
        user.setPassword("encoded1234");
        user.setRole(Role.USER);

        when(userRepository.findByEmail("test@mail.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("1234", "encoded1234"))
                .thenReturn(true);

        when(jwtUtil.generateToken("test@mail.com", "USER"))
                .thenReturn("mocked-jwt-token");

        String token = authService.login(request);

        assertEquals("mocked-jwt-token", token);
    }

    // ❌ USER NOT FOUND
    @Test
    void testLogin_UserNotFound() {
        LoginRequest request = new LoginRequest();
        request.setEmail("wrong@mail.com");
        request.setPassword("1234");

        when(userRepository.findByEmail("wrong@mail.com"))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.login(request);
        });

        assertEquals("User not found", exception.getMessage());
    }

    // ❌ INVALID PASSWORD
    @Test
    void testLogin_InvalidPassword() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@mail.com");
        request.setPassword("wrong");

        User user = new User();
        user.setEmail("test@mail.com");
        user.setPassword("encoded1234");

        when(userRepository.findByEmail("test@mail.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("wrong", "encoded1234"))
                .thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.login(request);
        });

        assertEquals("Invalid credentials", exception.getMessage());
    }
}