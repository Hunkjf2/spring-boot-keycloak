package com.example.keycloak.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API REST CADASTRO DE PESSOA AUTH KEYCLOAK")
                        .description("Módulo de Pessoa")
                        .version("v1.0.0"));
    }

}
