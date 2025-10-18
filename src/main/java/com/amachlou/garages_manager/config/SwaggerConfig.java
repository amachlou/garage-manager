package com.amachlou.garages_manager.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi garageApi() {
        return GroupedOpenApi.builder()
                .group("Garage-api")
                .packagesToScan("com.amachlou.garages_manager.controller")
                .build();
    }
}
