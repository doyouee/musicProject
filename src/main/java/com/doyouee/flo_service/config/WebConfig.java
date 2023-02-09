package com.doyouee.flo_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//CORS

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 모든 매핑경로에 대해 (addMapping("/**"))
        // 모든 사용자에 대해 (allowedOrigins("*"))
        // GET, POST, PUT, DELETE, PATCH, OPTION
        // 모든 메서드를 허용한다 (allowedMethos("*"))
        // 프론트와 협업 시에는 이렇게 오픈시켜주어야 한다.
        registry.addMapping("/**").allowedOrigins("*")
            .allowedMethods("*");
    }
}
