package project.seatsence.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

// @OpenAPIDefinition(
//        info =
//                @Info(
//                        title = "Seat Sence API 명세서",
//                        description = "실시간 자리 확인 및 좌석 관리 서비스 API 명세서",
//                        version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {
    //    @Bean
    //    public GroupedOpenApi seatSenceOpenApi() {
    //        String[] paths = {"api/v1/**"};
    //
    //        return GroupedOpenApi.builder()
    //                .group("실시간 자리 확인 및 좌석 관리 서비스 API v1")
    //                .pathsToMatch(paths)
    //                .build();
    //    }

    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String version) {

        Info info =
                new Info()
                        .title("Seat Sence API 명세서")
                        .version(version)
                        .description("실시간 자리 확인 및 좌석 관리 서비스 API 명세서") // 문서 설명
                        .contact(
                                new Contact()
                                        .name("benjamin(seat-sense)")
                                        .email("chosj1526@gmail.com"));

        // Security 스키마 설정
        SecurityScheme bearerAuth =
                new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("Bearer ")
                        .bearerFormat("JWT")
                        .in(SecurityScheme.In.HEADER)
                        .name(HttpHeaders.AUTHORIZATION);

        // Security 요청 설정
        SecurityRequirement addSecurityItem = new SecurityRequirement();
        addSecurityItem.addList("JWT");

        return new OpenAPI()
                // Security 인증 컴포넌트 설정
                .components(new Components().addSecuritySchemes("JWT", bearerAuth))
                // API 마다 Security 인증 컴포넌트 설정
                .addSecurityItem(addSecurityItem)
                .info(info);
    }
}
