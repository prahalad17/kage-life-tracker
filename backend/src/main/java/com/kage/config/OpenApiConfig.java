package com.kage.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Productivity App",
                version = "1.0",
                description = "Productivity App Microservices",
                termsOfService = "http://swagger.io/terms/",
                contact = @Contact(
                        name = "Prahalad P",
                        email = "prahaladwork@gmail.com",
                        url = "https://github.com/prahalad17"
                ),
                license = @License(
                        name = "Apache License, Version 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Local Development"),
//	        @Server(url = "https://staging.example.com", description = "Staging Server"),
//	        @Server(url = "https://api.example.com", description = "Production Server")
        }
)
public class OpenApiConfig {

    public static final String SECURITY_SCHEME_NAME = "BearerAuth";

    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI()
                .addSecurityItem(
                        new SecurityRequirement().addList(SECURITY_SCHEME_NAME)
                )
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        SECURITY_SCHEME_NAME,
                                        new SecurityScheme()
                                                .name("Authorization")
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );
    }

//    private static final String SECURITY_SCHEME_NAME = "bearerAuth";
//    private static final String SCHEME = "bearer";
//    private static final String BEARER_FORMAT = "JWT";
//
//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .addSecurityItem(new SecurityRequirement()
//                        .addList(SECURITY_SCHEME_NAME, List.of("read", "write")))
//                .components(new Components()
//                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
//                                new SecurityScheme()
//                                        .name(SECURITY_SCHEME_NAME)
//                                        .type(SecurityScheme.Type.HTTP)
//                                        .scheme(SCHEME)
//                                        .bearerFormat(BEARER_FORMAT)
//                        )
//                );
//    }
}
