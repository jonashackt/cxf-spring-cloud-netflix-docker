package de.jonashackt.configuration;

import de.codecentric.namespace.weatherservice.WeatherService;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeatherclientConfiguration {

    @Bean
    public WeatherService weatherServiceClient() {
        JaxWsProxyFactoryBean jaxWsFactory = new JaxWsProxyFactoryBean();
        jaxWsFactory.setServiceClass(WeatherService.class);
        // weatherservice routed over Zuul address
        jaxWsFactory.setAddress("http://localhost:8080/api/weatherservice/soap/Weather");
        return (WeatherService) jaxWsFactory.create();
    }
}
