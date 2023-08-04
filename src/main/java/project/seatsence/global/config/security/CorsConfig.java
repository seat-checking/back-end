package project.seatsence.global.config.security;

import static project.seatsence.global.constants.Constants.COOKIE_NAME_PREFIX_SECURE;
import static project.seatsence.global.constants.Constants.REFRESH_TOKEN_NAME;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:3000",
                        "http://localhost:8080",
                        "https://seat-sense.site:8080")
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.PATCH.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.OPTIONS.name())
                .exposedHeaders(COOKIE_NAME_PREFIX_SECURE + REFRESH_TOKEN_NAME)
                .allowCredentials(true); // 쿠키 인증 요청 허용
    }
}
