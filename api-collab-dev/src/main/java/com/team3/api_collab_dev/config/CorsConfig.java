package com.team3.api_collab_dev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // autorise tous les endpoints
                        .allowedOrigins("http://localhost:4200") // autorise ton Angular
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // méthodes autorisées
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
