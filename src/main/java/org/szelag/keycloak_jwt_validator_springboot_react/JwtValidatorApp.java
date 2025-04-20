package org.szelag.keycloak_jwt_validator_springboot_react;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JwtValidatorApp {

    private static final Logger logger = LoggerFactory.getLogger(JwtValidatorApp.class);
    public static void main(String[] args) {
        logger.info("Starting Keycloack JWT Validator Spring React Application...");
        SpringApplication.run(JwtValidatorApp.class, args);
    }
}