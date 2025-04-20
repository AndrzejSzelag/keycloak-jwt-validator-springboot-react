package org.szelag.keycloak_jwt_validator_springboot_react.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class EnvConfig {

    private static final Logger logger = LoggerFactory.getLogger(EnvConfig.class);

    @PostConstruct
    public void loadEnvFile() {
        logger.info("Loading environment variables from .env file.");
        try (Stream<String> lines = Files.lines(Paths.get(".env"), StandardCharsets.UTF_8)) {
            lines.filter(line -> line.contains("=") && !line.trim().startsWith("#"))
                    .forEach(line -> {
                        String[] parts = line.split("=", 2);
                        if (parts.length == 2) {
                            String key = parts[0].trim();
                            String value = parts[1].trim();
                            System.setProperty(key, value);
                            System.setProperty(key, value);
                            logger.debug("Loaded environment variable: {}", key);                                                      
                        } else {
                            logger.warn("Skipping invalid line in .env file: {}", line);
                        }
                    });
            logger.info(".env file loaded successfully.");
        } catch (IOException e) {
            logger.warn("Could not load .env file: {}", e.getMessage());
        }
    }
}