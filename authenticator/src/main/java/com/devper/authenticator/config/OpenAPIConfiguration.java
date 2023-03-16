package com.devper.authenticator.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {
    @Bean
    public OpenAPI getOpenApi() {
        SecurityScheme jwtScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("jwt"))
                .components(new Components().addSecuritySchemes("jwt", jwtScheme))
                .info(new Info()
                        .title("AUTHENTICATOR")
                        .description("Authenticator server")
                        .version("v1.0")
                        .license(new License().name("NO LICENSE").url("http://khoanguyendac.dev"))
                        .contact(new Contact()
                                .email("ndkhoalk97@gmail.com")
                                .name("Nguyen Dac Khoa")
                                .url("http://khoanguyendac.dev")))
                .externalDocs(new ExternalDocumentation()
                        .description("Spring Documentation")
                        .url("https://docs.spring.io/spring-framework/docs/current/reference/html/"));
    }
}
