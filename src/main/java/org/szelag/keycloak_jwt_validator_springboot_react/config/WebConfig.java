package org.szelag.keycloak_jwt_validator_springboot_react.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Global CORS configuration for the application.
 * This setup allows cross-origin requests from the specified origin (http://localhost:3000).
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        logger.info("CORS configuration applied: allowing http://localhost:3000 for GET requests");

        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET")
                .allowedHeaders("Authorization", "Content-Type")
                .allowCredentials(true);
    }
}