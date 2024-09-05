package co.istad.advanced_jpa.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Sattya
 * create at 9/5/2024 10:32 PM
 */
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
    @Value("${spring.security.allowed-origin}")
    private String allowedOrigin;
    @Bean
    public OpenAPI customizeOpenAPI(){
        Server server = new Server().url(allowedOrigin);
        return new OpenAPI()
                .info(new Info()
                        .title("Advanced JPA API")
                        .version("1.0")
                        .description("Advanced API documentation with detailed information integration with Spring HATEOS.")
                        .contact(
                                new Contact()
                                        .name("Piseth Sattya")
                                        .email("pisethsattya33@gmail.com")
                        )
                )
                .tags(List.of(
                        new Tag()
                                .name("Product")
                                .description("Operations related to the products and services available on Spring HATEOS")
                ))
                .servers(List.of(server));
    }
}
