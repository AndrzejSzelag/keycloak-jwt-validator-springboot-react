package org.szelag.keycloak_jwt_validator_springboot_react.controller;

import java.time.Instant;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.szelag.keycloak_jwt_validator_springboot_react.model.TokenResult;

@RestController
@RequestMapping("${api.prefix}/jwt")
public class JwtController {

    private static final Logger logger = LoggerFactory.getLogger(JwtController.class);
    private final JwtDecoder jwtDecoder;

    public JwtController(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
        logger.info("JwtController initialized");
    }

    @GetMapping(value = "/validate", produces = "application/json")
    public ResponseEntity<Map<String, Object>> validateToken(
            @RequestHeader(name = "Authorization", required = false) String authHeader) {
        logger.info("Request to /validate");

        if (authHeader == null || authHeader.trim().isEmpty() || !authHeader.startsWith("Bearer ")) {
            logger.info("No valid JWT token provided, assuming public access.");
            return ResponseEntity.ok(Map.of("status", "public", "message", "Endpoint accessible without JWT."));
        }

        var tokenResult = extractTokenFromHeader(authHeader);

        if (tokenResult.hasError()) {
            logger.warn("Validation failed: {}", tokenResult.errorMessage());
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", tokenResult.errorMessage()));
        }

        try {
            var jwt = jwtDecoder.decode(tokenResult.token());

            if (jwt.getExpiresAt() != null && jwt.getExpiresAt().isBefore(Instant.now())) {
                logger.warn("Token expired at: {}", jwt.getExpiresAt());
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of(
                                "valid", false,
                                "error", "Token expired at " + jwt.getExpiresAt()
                        ));
            }

            logger.info("Token validated for subject: {}", jwt.getSubject());
            return ResponseEntity.ok(Map.of(
                    "valid", true,
                    "issuer", jwt.getIssuer() != null ? jwt.getIssuer().toString() : "none",
                    "subject", jwt.getSubject() != null ? jwt.getSubject() : "none",
                    "expiration", jwt.getExpiresAt() != null ? jwt.getExpiresAt().toString() : "none",
                    "issuedAt", jwt.getIssuedAt() != null ? jwt.getIssuedAt().toString() : "none"
            ));
        } catch (JwtValidationException e) {
            logger.warn("Token validation error: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "valid", false,
                            "error", "Token validation error: " + e.getMessage()
                    ));
        } catch (JwtException e) {
            logger.warn("Invalid token: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "valid", false,
                            "error", "Invalid token: " + e.getMessage()
                    ));
        } catch (Exception e) {
            logger.error("Server error during token validation", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "valid", false,
                            "error", "Internal server error during token validation"
                    ));
        }
    }

    private TokenResult extractTokenFromHeader(String authHeader) {
        if (authHeader == null) {
            logger.warn("Authorization header missing");
            return TokenResult.error("No JWT token provided");
        }
        if (!authHeader.startsWith("Bearer ")) {
            logger.warn("Incorrect token format");
            return TokenResult.error("Incorrect token format - missing 'Bearer' prefix");
        }

        String token = authHeader.substring(7);
        if (token.trim().isEmpty()) {
            logger.warn("JWT token is empty after Bearer");
            return TokenResult.error("JWT token is empty");
        }
        return TokenResult.success(token);
    }
}