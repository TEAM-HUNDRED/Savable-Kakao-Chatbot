package com.management.web.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Savable Web API Document")
                .version("v1.0.0")
                .description("Savable 개인 관리 Web API 명세서입니다.");
        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
