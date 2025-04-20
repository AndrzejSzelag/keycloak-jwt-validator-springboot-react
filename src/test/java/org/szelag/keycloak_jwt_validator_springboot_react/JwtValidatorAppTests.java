package org.szelag.keycloak_jwt_validator_springboot_react;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.szelag.keycloak_jwt_validator_springboot_react.controller.JwtController;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JwtValidatorAppTests {

    @Autowired
    private JwtDecoder jwtDecoder;

    @Autowired
    private JwtController jwtController;

    @Value("${jwk.set.uri}")
    private String jwkSetUri;

    @Value("${api.prefix}")
    private String apiPrefix;

    @Test
    void contextLoads() {
    }

    @Test
    void mainMethodExecutesWithoutException() {
        JwtValidatorApp.main(new String[]{"--server.port=0"});
    }

    @Test
    void mainMethodCallsSpringApplicationRun() {
        try (MockedStatic<SpringApplication> mocked = Mockito.mockStatic(SpringApplication.class)) {
            JwtValidatorApp.main(new String[]{});
            mocked.verify(()
                    -> SpringApplication.run(JwtValidatorApp.class, new String[]{}));
        }
    }

    @Test
    void jwtDecoderBeanShouldBeCreated() {
        assertNotNull(jwtDecoder);
    }

    @Test
    void jwtControllerBeanShouldBeCreated() {
        assertNotNull(jwtController);
    }

    @Test
    void jwkSetUriShouldBeLoadedFromConfig() {
        assertNotNull(jwkSetUri);
    }

    @Test
    void apiPrefixShouldBeLoadedFromConfig() {
        assertEquals("/api/v1", apiPrefix);
    }
}
