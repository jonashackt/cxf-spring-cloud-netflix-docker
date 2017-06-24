package de.jonashackt.configuration;

import de.codecentric.namespace.weatherservice.WeatherService;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.yml")
public class WeatherclientConfiguration {

    @Value("${weatherservice.host:localhost}")
    private String host;

    @Value("${weatherservice.port:48080}")
    private String port;

    private final String urlending = "/api/weatherservice/soap/Weather";

    @Bean
    public WeatherService weatherServiceClient() {
        JaxWsProxyFactoryBean jaxWsFactory = new JaxWsProxyFactoryBean();
        jaxWsFactory.setServiceClass(WeatherService.class);
        jaxWsFactory.setAddress( "http://" + host + ":" + port + urlending);
        return (WeatherService) jaxWsFactory.create();
    }
}
