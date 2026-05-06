package com.et20.escolar.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.*;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Sistema de Gestión Escolar – ET N° 20")
                .description("API RESTful para gestión de Estudiantes, Materias y Calificaciones. " +
                             "Primero hacé POST /api/login para obtener el token JWT.")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Nicolas Nahuel Fernandez Bogarin"))
            )
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
            .components(new Components()
                .addSecuritySchemes("bearerAuth",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"))
            );
    }
}