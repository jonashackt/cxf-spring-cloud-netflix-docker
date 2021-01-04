package de.jonashackt.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "weatherclient",
                version = "0.0.1",
                description = "Just a simple Weatherservice Testclient"
        )
)
public class OpenAPIConfig {
}
