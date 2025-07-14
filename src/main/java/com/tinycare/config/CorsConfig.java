package com.tinycare.config;

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
                System.out.println(">>> Applying global CORS configuration");

                registry.addMapping("/**")
                        .allowedOrigins(
                                "http://localhost:3000",
                                "https://meek-pasca-7094d7.netlify.app"
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .exposedHeaders("Authorization") // optional, if using tokens
                        .allowCredentials(true)
                        .maxAge(3600); // cache preflight responses
            }
        };
    }
}
