package org.szelag.keycloak_jwt_validator_springboot_react.model;


public record TokenResult(String token, String errorMessage) {

    public static TokenResult success(String token) {
        return new TokenResult(token, null);
    }

    public static TokenResult error(String errorMessage) {
        return new TokenResult(null, errorMessage);
    }

    public boolean hasError() {
        return errorMessage != null;
    }
}