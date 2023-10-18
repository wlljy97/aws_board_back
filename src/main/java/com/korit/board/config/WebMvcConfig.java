package com.korit.board.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // cors 풀어줌
        registry.addMapping("/**")      // 요청 엔드포인트
                .allowedMethods("*")    // 요청 메소드
                .allowedOrigins("*");   // 요청 서버
    }
}
