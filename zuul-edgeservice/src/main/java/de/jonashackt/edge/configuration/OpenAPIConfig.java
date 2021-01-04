package de.jonashackt.edge.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "zuul-edgeservice",
                version = "0.0.1",
                description = "Our cool Zuul Edgeservice with dynamic Eureka-registered routes"
        )
)
public class OpenAPIConfig {
}
