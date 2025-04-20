package org.szelag.keycloak_jwt_validator_springboot_react.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    private final CorsConfig corsConfig;

    public SecurityConfig(CorsConfig corsConfig) {
        this.corsConfig = corsConfig;
        logger.info("SecurityConfig initialized with CorsConfig: {}", corsConfig);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        logger.info("Building SecurityFilterChain in SecurityConfig...");
        http
                .cors(corsCustomizer -> {
                    logger.info("Configuring CORS with CorsConfigurationSource from CorsConfig");
                    corsCustomizer.configurationSource(corsConfig.corsConfigurationSource());
                })
                .authorizeHttpRequests(authorize -> {
                    logger.info("Configuring authorization rules...");
                    authorize
                            .requestMatchers("/api/v1/jwt/validate").permitAll()
                            .anyRequest().authenticated();
                })
                .oauth2ResourceServer(oauth2 -> {
                    logger.info("Configuring OAuth2 Resource Server with JWT defaults");
                    oauth2.jwt(withDefaults());
                });
        logger.info("SecurityFilterChain built successfully in SecurityConfig.");
        return http.build();
    }
}
