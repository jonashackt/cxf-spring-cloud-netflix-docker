package de.jonashackt;

import de.codecentric.cxf.xmlvalidation.CustomFaultBuilder;
import de.jonashackt.fault.WeatherFaultBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableDiscoveryClient
@SpringBootApplication
public class WeatherServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherServiceApplication.class, args);
    }

    // Activating XML-Schema validation with custom Fault
    @Bean
    public CustomFaultBuilder weatherFaultBuilder() {
        return new WeatherFaultBuilder();
    }
}
