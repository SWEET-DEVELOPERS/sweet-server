package org.sopt.sweet.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI publicApi() {
        // 기본적인 정보 생성
        Info info = new Info()
                .title("Sweet")
                .description("Sweet의 API 명세서입니다.")
                .version("v1");
        // jwt에 대한 SecurityScheme 설정, Swagger-ui에서 token 인증이 가능=
        SecurityScheme securityScheme = new SecurityScheme()
                .name("Authorization")
                .type(SecurityScheme.Type.HTTP)
                .in(HEADER)
                .bearerFormat("JWT")
                .scheme("Bearer");

        Components components = new Components().addSecuritySchemes("token", securityScheme);

        return new OpenAPI()
                .info(info)
                .components(components);
    }
}
