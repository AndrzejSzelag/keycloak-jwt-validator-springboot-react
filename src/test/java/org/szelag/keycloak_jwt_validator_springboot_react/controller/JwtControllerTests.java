package org.szelag.keycloak_jwt_validator_springboot_react.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.JwtValidationException;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtControllerTests {

    @Mock
    private JwtDecoder jwtDecoder;

    @InjectMocks
    private JwtController jwtController;

    private Jwt validJwt;
    private final String VALID_TOKEN = "valid.jwt.token";
    private final String VALID_AUTH_HEADER = "Bearer " + VALID_TOKEN;

    @BeforeEach
    void setUp() {
        Instant now = Instant.now();
        validJwt = Jwt.withTokenValue(VALID_TOKEN)
                .header("alg", "RS256")
                .subject("user123")
                .issuer("https://auth.example.com")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(3600))
                .build();
    }

    @Test
    void testValidateToken_withNoAuthHeader() {
        // Given
        String authHeader = null;

        // When
        ResponseEntity<Map<String, Object>> response = jwtController.validateToken(authHeader);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("public", response.getBody().get("status"));
        assertEquals("Endpoint accessible without JWT.", response.getBody().get("message"));
        
        // Verify that decoder was never called
        verifyNoInteractions(jwtDecoder);
    }

    @Test
    void testValidateToken_withEmptyAuthHeader() {
        // Given
        String authHeader = "";

        // When
        ResponseEntity<Map<String, Object>> response = jwtController.validateToken(authHeader);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("public", response.getBody().get("status"));
        assertEquals("Endpoint accessible without JWT.", response.getBody().get("message"));
        
        // Verify that decoder was never called
        verifyNoInteractions(jwtDecoder);
    }

    @Test
    void testValidateToken_withInvalidPrefix() {
        // Given
        String authHeader = "Token " + VALID_TOKEN;

        // When
        ResponseEntity<Map<String, Object>> response = jwtController.validateToken(authHeader);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("public", response.getBody().get("status"));
        assertEquals("Endpoint accessible without JWT.", response.getBody().get("message"));
        
        // Verify that decoder was never called
        verifyNoInteractions(jwtDecoder);
    }

    @Test
    void testValidateToken_withEmptyToken() {
        // Given
        String authHeader = "Bearer ";

        // When
        ResponseEntity<Map<String, Object>> response = jwtController.validateToken(authHeader);

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("JWT token is empty", response.getBody().get("error"));
        
        // Verify that decoder was never called
        verifyNoInteractions(jwtDecoder);
    }

    @Test
    void testValidateToken_withValidToken() {
        // Given
        when(jwtDecoder.decode(VALID_TOKEN)).thenReturn(validJwt);

        // When
        ResponseEntity<Map<String, Object>> response = jwtController.validateToken(VALID_AUTH_HEADER);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get("valid"));
        assertEquals("https://auth.example.com", response.getBody().get("issuer"));
        assertEquals("user123", response.getBody().get("subject"));
        assertNotNull(response.getBody().get("expiration"));
        assertNotNull(response.getBody().get("issuedAt"));

        verify(jwtDecoder, times(1)).decode(VALID_TOKEN);
    }

    @Test
    void testValidateToken_withExpiredToken() {
        // Given
        Jwt expiredJwt = Jwt.withTokenValue(VALID_TOKEN)
                .header("alg", "RS256")
                .subject("user123")
                .issuer("https://auth.example.com")
                .issuedAt(Instant.now().minusSeconds(7200))
                .expiresAt(Instant.now().minusSeconds(3600))
                .build();

        when(jwtDecoder.decode(VALID_TOKEN)).thenReturn(expiredJwt);

        // When
        ResponseEntity<Map<String, Object>> response = jwtController.validateToken(VALID_AUTH_HEADER);

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertFalse((Boolean) response.getBody().get("valid"));
        assertTrue(response.getBody().get("error").toString().contains("Token expired at"));

        verify(jwtDecoder, times(1)).decode(VALID_TOKEN);
    }

    @Test
    void testValidateToken_withJwtValidationException() {
        // Given
        List<OAuth2Error> errors = List.of(new OAuth2Error("invalid_token", "Invalid signature", null));
        JwtValidationException exception = new JwtValidationException("Invalid signature", errors);
        when(jwtDecoder.decode(VALID_TOKEN)).thenThrow(exception);

        // When
        ResponseEntity<Map<String, Object>> response = jwtController.validateToken(VALID_AUTH_HEADER);

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertFalse((Boolean) response.getBody().get("valid"));
        assertTrue(response.getBody().get("error").toString().contains("Token validation error"));

        verify(jwtDecoder, times(1)).decode(VALID_TOKEN);
    }

    @Test
    void testValidateToken_withJwtException() {
        // Given
        JwtException exception = new JwtException("Malformed JWT token");
        when(jwtDecoder.decode(VALID_TOKEN)).thenThrow(exception);

        // When
        ResponseEntity<Map<String, Object>> response = jwtController.validateToken(VALID_AUTH_HEADER);

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertFalse((Boolean) response.getBody().get("valid"));
        assertTrue(response.getBody().get("error").toString().contains("Invalid token"));

        verify(jwtDecoder, times(1)).decode(VALID_TOKEN);
    }

    @Test
    void testValidateToken_withGenericException() {
        // Given
        RuntimeException exception = new RuntimeException("Unexpected error");
        when(jwtDecoder.decode(VALID_TOKEN)).thenThrow(exception);

        // When
        ResponseEntity<Map<String, Object>> response = jwtController.validateToken(VALID_AUTH_HEADER);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertFalse((Boolean) response.getBody().get("valid"));
        assertEquals("Internal server error during token validation", response.getBody().get("error"));

        verify(jwtDecoder, times(1)).decode(VALID_TOKEN);
    }

    @Test
    void testValidateToken_withTokenWithoutClaims() {
        // Given
        Jwt minimalJwt = Jwt.withTokenValue(VALID_TOKEN)
                .header("alg", "RS256")
                .claim("jti", "1234567890")
                .build();

        when(jwtDecoder.decode(VALID_TOKEN)).thenReturn(minimalJwt);

        // When
        ResponseEntity<Map<String, Object>> response = jwtController.validateToken(VALID_AUTH_HEADER);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get("valid"));
        assertEquals("none", response.getBody().get("issuer"));
        assertEquals("none", response.getBody().get("subject"));
        assertEquals("none", response.getBody().get("expiration"));
        assertEquals("none", response.getBody().get("issuedAt"));

        verify(jwtDecoder, times(1)).decode(VALID_TOKEN);
    }
}