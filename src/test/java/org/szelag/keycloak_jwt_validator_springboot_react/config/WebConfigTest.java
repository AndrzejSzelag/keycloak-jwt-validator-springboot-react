package org.szelag.keycloak_jwt_validator_springboot_react.config;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfigTest {

    @Test
    public void testCorsConfiguration() {
        CorsRegistry registry = mock(CorsRegistry.class);
        CorsRegistration corsRegistration = mock(CorsRegistration.class);

        when(registry.addMapping("/**")).thenReturn(corsRegistration);
        when(corsRegistration.allowedOrigins("http://localhost:3000")).thenReturn(corsRegistration);
        when(corsRegistration.allowedMethods("GET")).thenReturn(corsRegistration);
        when(corsRegistration.allowedHeaders("Authorization", "Content-Type")).thenReturn(corsRegistration);
        when(corsRegistration.allowCredentials(true)).thenReturn(corsRegistration);

        WebConfig webConfig = new WebConfig();

        webConfig.addCorsMappings(registry);

        verify(registry).addMapping("/**");
        verify(corsRegistration).allowedOrigins("http://localhost:3000");
        verify(corsRegistration).allowedMethods("GET");
        verify(corsRegistration).allowedHeaders("Authorization", "Content-Type");
        verify(corsRegistration).allowCredentials(true);
    }

    @Test
    public void testCorsRegistrationWithSpringContext() {
        try (AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext()) {
            context.register(WebConfig.class);
            context.setServletContext(new MockServletContext());
            context.refresh();
            
            WebConfig webConfig = context.getBean(WebConfig.class);
            assert (webConfig instanceof WebMvcConfigurer);
        }
    }

    @Test
    public void testWebMvcConfigurerImplementation() {
        WebConfig webConfig = new WebConfig();
        assert (webConfig instanceof WebMvcConfigurer);
    }
}