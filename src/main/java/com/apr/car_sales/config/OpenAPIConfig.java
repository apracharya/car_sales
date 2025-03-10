package com.apr.car_sales.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TuboGate cars.")
                        .version("1.0")
                        .description("Car sales app by Apurva [TurboGate Cars].")
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                )
                .servers(Arrays.asList(new Server().url("http://localhost:8080").description("local"),
                        new Server().url("https://backendhostedurl").description("live"))
                )
//                .tags(Arrays.asList(
//                        new Tag().name("Auth APIs"), // ui ma chaiya order ma rakhni
//                        new Tag().name("Course APIs"),
//                        new Tag().name("Enrollment APIs"),
//                        new Tag().name("Assignment APIs"),
//                        new Tag().name("Submission APIs"),
//                        new Tag().name("Material APIs")
//                ))
                .addSecurityItem(new SecurityRequirement().addList("jwt"))
                .components(new Components().addSecuritySchemes(
                        "jwtCookieAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.COOKIE)
                                .name("jwt")
                ));
    }
}
