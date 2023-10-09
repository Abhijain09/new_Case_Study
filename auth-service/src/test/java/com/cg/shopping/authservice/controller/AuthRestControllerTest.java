package com.cg.shopping.authservice.controller;

import com.cg.shopping.authservice.client.ProfileServiceClient;
import com.cg.shopping.authservice.client.UserProfile;
import com.cg.shopping.authservice.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthRestControllerTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private ProfileServiceClient profileServiceClient;

    @InjectMocks
    private AuthRestController authRestController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginValidCredentials() {
        // Arrange
        Map<String, String> user = new HashMap<>();
        user.put("email", "test@example.com");
        user.put("password", "password");

        UserProfile userProfile = new UserProfile();
        userProfile.setEmail("test@example.com");
        userProfile.setRole("USER");

        when(profileServiceClient.login(user)).thenReturn(new ResponseEntity<>(userProfile, HttpStatus.OK));
        when(jwtUtil.generateToken("test@example.com", "USER")).thenReturn("mockedToken");

        // Act
        ResponseEntity<String> response = authRestController.login(user);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("mockedToken", response.getBody());

        // Verify that the methods were called with the expected arguments
        verify(profileServiceClient, times(1)).login(user);
        verify(jwtUtil, times(1)).generateToken("test@example.com", "USER");
    }

    @Test
    public void testLoginInvalidCredentials() {
        // Arrange
        Map<String, String> user = new HashMap<>();
        user.put("email", "test@example.com");
        user.put("password", "password");

        when(profileServiceClient.login(user)).thenReturn(new ResponseEntity<>(new UserProfile(), HttpStatus.BAD_REQUEST));

        // Act
        ResponseEntity<String> response = authRestController.login(user);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Invalid Credential Provided", response.getBody());

        // Verify that the methods were called with the expected arguments
        verify(profileServiceClient, times(1)).login(user);
        verify(jwtUtil, never()).generateToken(anyString(), anyString());
    }

    @Test
    public void testRegisterUserSuccessfully() {
        // Arrange
        UserProfile user = new UserProfile();
        user.setEmail("test@example.com");
        user.setRole("USER");

        when(profileServiceClient.addNewCustomer(user)).thenReturn(new ResponseEntity<>(user, HttpStatus.OK));

        // Act
        ResponseEntity<String> response = authRestController.register(user);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User Registered Successfully", response.getBody());

        // Verify that the methods were called with the expected arguments
        verify(profileServiceClient, times(1)).addNewCustomer(user);
        verify(jwtUtil, never()).generateToken(anyString(), anyString());
    }

    @Test
    public void testRegisterUserFailure() {
        // Arrange
        UserProfile user = new UserProfile();
        user.setEmail("test@example.com");
        user.setRole("USER");

        when(profileServiceClient.addNewCustomer(user)).thenReturn(new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));

        // Act
        ResponseEntity<String> response = authRestController.register(user);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Error occurred while registering user", response.getBody());

        // Verify that the methods were called with the expected arguments
        verify(profileServiceClient, times(1)).addNewCustomer(user);
        verify(jwtUtil, never()).generateToken(anyString(), anyString());
    }

}
