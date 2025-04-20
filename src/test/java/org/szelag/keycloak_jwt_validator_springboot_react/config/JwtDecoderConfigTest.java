package org.szelag.keycloak_jwt_validator_springboot_react.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class JwtDecoderConfigTest {

    @InjectMocks
    private JwtDecoderConfig jwtDecoderConfig;

    @Test
    void testJwtDecoderCreation() {
        // Given
        String testJwkSetUri = "https://test-auth-server/realms/test-realm/protocol/openid-connect/certs";
        ReflectionTestUtils.setField(jwtDecoderConfig, "jwkSetUri", testJwkSetUri);

        // When
        NimbusJwtDecoder decoder = jwtDecoderConfig.jwtDecoder();

        // Then
        assertNotNull(decoder, "JWT decoder should not be null");
    }

    @Test
    void testJwtDecoderWithMockStatic() {
        try (var nimbusJwtDecoderStaticMock = mockStatic(NimbusJwtDecoder.class)) {
            // Given
            String testJwkSetUri = "https://test-auth-server/realms/test-realm/protocol/openid-connect/certs";
            ReflectionTestUtils.setField(jwtDecoderConfig, "jwkSetUri", testJwkSetUri);

            var builderMock = mock(NimbusJwtDecoder.JwkSetUriJwtDecoderBuilder.class);
            var decoderMock = mock(NimbusJwtDecoder.class);

            nimbusJwtDecoderStaticMock.when(() -> NimbusJwtDecoder.withJwkSetUri(testJwkSetUri))
                    .thenReturn(builderMock);
            when(builderMock.build()).thenReturn(decoderMock);

            // When
            NimbusJwtDecoder result = jwtDecoderConfig.jwtDecoder();

            // Then
            assertSame(decoderMock, result, "Should return the mocked decoder");

            // Verify
            nimbusJwtDecoderStaticMock.verify(() -> NimbusJwtDecoder.withJwkSetUri(testJwkSetUri));
            verify(builderMock).build();
        }
    }

    @Test
    void testJwkSetUriProperty() {
        // Given
        String expectedUri = "https://expected-url/certs";

        // When
        ReflectionTestUtils.setField(jwtDecoderConfig, "jwkSetUri", expectedUri);
        String actualUri = (String) ReflectionTestUtils.getField(jwtDecoderConfig, "jwkSetUri");

        // Then
        assertEquals(expectedUri, actualUri, "The jwkSetUri should be properly set");
    }
}