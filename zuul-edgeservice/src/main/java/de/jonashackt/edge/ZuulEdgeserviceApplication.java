package de.jonashackt.edge;

import de.jonashackt.edge.filters.pre.LoggingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
public class ZuulEdgeserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulEdgeserviceApplication.class, args);
	}

	@Bean
	public LoggingFilter loggingFilter() {
		return new LoggingFilter();
	}
}

