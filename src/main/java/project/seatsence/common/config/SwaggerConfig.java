package project.seatsence.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info =
                @Info(
                        title = "Seat Sence API 명세서",
                        description = "실시간 자리 확인 및 좌석 관리 서비스 API 명세서",
                        version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi seatSenceOpenApi() {
        String[] paths = {"api/v1/**"};

        return GroupedOpenApi.builder()
                .group("실시간 자리 확인 및 좌석 관리 서비스 API v1")
                .pathsToMatch(paths)
                .build();
    }
}
