package org.szelag.keycloak_jwt_validator_springboot_react.config;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.slf4j.Logger;

@Configuration
public class JwtDecoderConfig {

    private static final Logger logger = LoggerFactory.getLogger(JwtDecoderConfig.class);

    // URL for downloading public keys (JWKS) from the Keycloak server
    @Value("${jwk.set.uri}")
    private String jwkSetUri;

    @Bean
    public NimbusJwtDecoder jwtDecoder() {
        logger.info("Creating NimbusJwtDecoder with jwkSetUri: {}", jwkSetUri);
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }
}