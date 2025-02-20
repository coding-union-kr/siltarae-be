package weavers.siltarae.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import weavers.siltarae.global.auth.AuthInterceptor;
import weavers.siltarae.global.auth.OptionalAuthInterceptor;
import weavers.siltarae.login.AuthArgumentResolver;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AuthConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;
    private final OptionalAuthInterceptor optionalAuthInterceptor;
    private final AuthArgumentResolver authArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/v1/**")
                .excludePathPatterns("/api/v1/login", "/api/vi/token", "/api/v1/feed");

        registry.addInterceptor(optionalAuthInterceptor)
                .addPathPatterns("/api/v1/feed");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authArgumentResolver);
    }
}
