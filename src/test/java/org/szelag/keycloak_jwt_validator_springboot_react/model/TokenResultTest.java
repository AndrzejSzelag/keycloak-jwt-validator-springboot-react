package org.szelag.keycloak_jwt_validator_springboot_react.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class TokenResultTest {

    @Test
    void successFactoryMethodShouldCreateSuccessTokenResult() {
        String testToken = "valid.jwt.token";
        TokenResult result = TokenResult.success(testToken);

        assertEquals(testToken, result.token());
        assertNull(result.errorMessage());
        assertFalse(result.hasError());
    }

    @Test
    void errorFactoryMethodShouldCreateErrorTokenResult() {
        String testErrorMessage = "Invalid token format";
        TokenResult result = TokenResult.error(testErrorMessage);

        assertNull(result.token());
        assertEquals(testErrorMessage, result.errorMessage());
        assertTrue(result.hasError());
    }

    @Test
    void hasErrorShouldReturnTrueWhenErrorMessageIsNotNull() {
        TokenResult result = new TokenResult(null, "An error occurred");
        assertTrue(result.hasError());
    }

    @Test
    void hasErrorShouldReturnFalseWhenErrorMessageIsNull() {
        TokenResult result = new TokenResult("some.token", null);
        assertFalse(result.hasError());
    }

    @Test
    void recordShouldStoreTokenAndErrorMessageCorrectly() {
        String testToken = "another.valid.token";
        String testErrorMessage = "Another error";
        TokenResult result = new TokenResult(testToken, testErrorMessage);

        assertEquals(testToken, result.token());
        assertEquals(testErrorMessage, result.errorMessage());
        assertTrue(result.hasError());
    }

    @Test
    void recordShouldHandleNullTokenAndErrorMessage() {
        TokenResult resultWithNulls = new TokenResult(null, null);
        assertNull(resultWithNulls.token());
        assertNull(resultWithNulls.errorMessage());
        assertFalse(resultWithNulls.hasError());

        TokenResult resultWithTokenOnly = new TokenResult("only.token", null);
        assertEquals("only.token", resultWithTokenOnly.token());
        assertNull(resultWithTokenOnly.errorMessage());
        assertFalse(resultWithTokenOnly.hasError());

        TokenResult resultWithErrorOnly = new TokenResult(null, "only.error");
        assertNull(resultWithErrorOnly.token());
        assertEquals("only.error", resultWithErrorOnly.errorMessage());
        assertTrue(resultWithErrorOnly.hasError());
    }
}