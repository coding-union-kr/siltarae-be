package weavers.siltarae.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@OpenAPIDefinition(
        info = @Info(
                title = "실타래 API 명세서",
                description = "API 명세서",
                version = "v1",
                contact = @Contact(
                        name = "weavers"
                )
        )
)
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        List<Server> servers = Stream.of(
                new Server().url("https://api-siltarae.store"),
                new Server().url("http://localhost:8080")
        ).collect(Collectors.toList());

        return new OpenAPI().servers(servers);
    }
}

