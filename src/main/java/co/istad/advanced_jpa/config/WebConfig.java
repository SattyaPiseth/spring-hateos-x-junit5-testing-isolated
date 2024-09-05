package co.istad.advanced_jpa.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author Sattya
 * create at 9/5/2024 5:29 PM
 */
@Configuration
@ConfigurationProperties(prefix = "spring.security")
@Data
public class WebConfig implements WebMvcConfigurer {
    private List<String> allowedOrigin;
    private List<String> allowedHeader;
    private List<String> allowedMethod;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Enable CORS only for your API endpoints
                .allowedOrigins(String.valueOf(allowedOrigin)) // Allow all trusted domains
                .allowedMethods(String.valueOf(allowedMethod)) // Restrict HTTP methods
                .allowedHeaders(String.valueOf(allowedHeader)) // You can restrict to specific headers like "Authorization"
                .allowCredentials(true) // Enable cookies or other credentials
                .maxAge(3600); // Cache pre-flight requests for 1 hour
    }
}
