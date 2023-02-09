package com.doyouee.flo_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI floOpenAPI() {
        // version, title, description 은 본인 마음대로 지정
        Info info = new Info().version("0.0.1").title("Music 서비스 API").description("Music서비스 Restful API 명세서");
        return new OpenAPI().info(info);
    }
}
