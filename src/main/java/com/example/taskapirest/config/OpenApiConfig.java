package com.example.taskapirest.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI taskApiInfo() {
        return new OpenAPI().info(new Info()
                .title("Task API REST")
                .version("1.0.0")
                .description("CRUD de tarefas - projeto acadêmico da disciplina de Desenvolvimento Web Back-end"));
    }
}