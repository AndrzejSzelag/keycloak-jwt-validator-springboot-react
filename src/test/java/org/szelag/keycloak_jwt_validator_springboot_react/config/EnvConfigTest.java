package org.szelag.keycloak_jwt_validator_springboot_react.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EnvConfigTest {

    private EnvConfig envConfig;

    @TempDir
    Path tempDir;

    private Properties originalProperties;
    private Path envFilePath;

    @BeforeEach
    void setUp() {
        envConfig = new EnvConfig();

        // Backup the original system properties
        originalProperties = new Properties();
        originalProperties.putAll(System.getProperties());
    }

    @AfterEach
    void tearDown() {
        // Restore the original system properties
        System.setProperties(originalProperties);
    }

    @Test
    void testLoadEnvFile_Success() throws IOException {
        // Given
        envFilePath = tempDir.resolve(".env");
        String envContent
                = "# This is a comment\n"
                + "TEST_KEY1=test_value1\n"
                + "TEST_KEY2=test_value2\n"
                + "   TEST_KEY3   =   test_value3   \n"
                + // Testy trimowania
                "EMPTY_VALUE=\n"
                + "# Another comment\n"
                + "INVALID_LINE\n"
                + "TEST_KEY4=value with spaces\n";

        Files.writeString(envFilePath, envContent);

        try (MockedStatic<Paths> mockedPaths = Mockito.mockStatic(Paths.class)) {
            mockedPaths.when(() -> Paths.get(".env")).thenReturn(envFilePath);

            // When
            envConfig.loadEnvFile();

            // Then
            assertEquals("test_value1", System.getProperty("TEST_KEY1"));
            assertEquals("test_value2", System.getProperty("TEST_KEY2"));
            assertEquals("test_value3", System.getProperty("TEST_KEY3"));
            assertEquals("", System.getProperty("EMPTY_VALUE"));
            assertEquals("value with spaces", System.getProperty("TEST_KEY4"));
            assertNull(System.getProperty("INVALID_LINE"));
        }
    }

    @Test
    void testLoadEnvFile_FileNotFound() {
        // Given
        Path nonExistentFile = tempDir.resolve("non-existent.env");

        try (MockedStatic<Paths> mockedPaths = Mockito.mockStatic(Paths.class)) {
            mockedPaths.when(() -> Paths.get(".env")).thenReturn(nonExistentFile);

            // When
            envConfig.loadEnvFile();

            // Then
            // We just verify that no exception is thrown
            // and the method completes successfully with a warning log
        }
    }

    @Test
    void testLoadEnvFile_IOException() throws IOException {
        // Given
        Path mockPath = mock(Path.class);

        try (MockedStatic<Paths> mockedPaths = Mockito.mockStatic(Paths.class); MockedStatic<Files> mockedFiles = Mockito.mockStatic(Files.class)) {

            mockedPaths.when(() -> Paths.get(".env")).thenReturn(mockPath);
            mockedFiles.when(() -> Files.lines(eq(mockPath), eq(StandardCharsets.UTF_8)))
                    .thenThrow(new IOException("Simulated IO error"));

            // When
            envConfig.loadEnvFile();

            // Then
            // We just verify that the exception is caught properly
            // and the method completes with a warning log
        }
    }

    @Test
    void testLoadEnvFile_SkipsComments() throws IOException {
        // Given
        envFilePath = tempDir.resolve(".env");
        String envContent
                = "# This should be skipped\n"
                + "TEST_KEY=test_value\n"
                + "  # This indented comment should also be skipped\n";

        Files.writeString(envFilePath, envContent);

        try (MockedStatic<Paths> mockedPaths = Mockito.mockStatic(Paths.class)) {
            mockedPaths.when(() -> Paths.get(".env")).thenReturn(envFilePath);

            // When
            envConfig.loadEnvFile();

            // Then
            assertEquals("test_value", System.getProperty("TEST_KEY"));
            assertNull(System.getProperty("# This should be skipped"));
            assertNull(System.getProperty("  # This indented comment should also be skipped"));
        }
    }

    @Test
    void testLoadEnvFile_IgnoresEmptyLines() throws IOException {
        // Given
        envFilePath = tempDir.resolve(".env");
        String envContent
                = "\n"
                + "TEST_KEY=test_value\n"
                + "\n"
                + "   \n"
                + // Pusta linia z białymi znakami
                "ANOTHER_KEY=another_value\n";

        Files.writeString(envFilePath, envContent);

        try (MockedStatic<Paths> mockedPaths = Mockito.mockStatic(Paths.class)) {
            mockedPaths.when(() -> Paths.get(".env")).thenReturn(envFilePath);

            // When
            envConfig.loadEnvFile();

            // Then
            assertEquals("test_value", System.getProperty("TEST_KEY"));
            assertEquals("another_value", System.getProperty("ANOTHER_KEY"));
        }
    }

    @Test
    void testLoadEnvFile_HandlesSpecialCharacters() throws IOException {
        // Given
        envFilePath = tempDir.resolve(".env");
        String envContent
                = "URL=http://example.com/api?param=value&other=123\n"
                + "JSON={\"key\": \"value\"}\n"
                + "PATH=/usr/local/bin:/usr/bin:/bin\n";

        Files.writeString(envFilePath, envContent);

        try (MockedStatic<Paths> mockedPaths = Mockito.mockStatic(Paths.class)) {
            mockedPaths.when(() -> Paths.get(".env")).thenReturn(envFilePath);

            // When
            envConfig.loadEnvFile();

            // Then
            assertEquals("http://example.com/api?param=value&other=123", System.getProperty("URL"));
            assertEquals("{\"key\": \"value\"}", System.getProperty("JSON"));
            assertEquals("/usr/local/bin:/usr/bin:/bin", System.getProperty("PATH"));
        }
    }
}