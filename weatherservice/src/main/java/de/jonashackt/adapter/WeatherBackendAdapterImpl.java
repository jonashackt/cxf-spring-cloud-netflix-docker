package de.jonashackt.adapter;

import de.jonashackt.internalmodel.GeneralOutlook;
import de.jonashackt.internalmodel.Weather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherBackendAdapterImpl implements WeatherBackendAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherBackendAdapterImpl.class);
    private final String url = "http://localhost:8090/weather/general/outlook";

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public GeneralOutlook generateGeneralOutlook(Weather weather) {
        LOG.info("Call weatherbackend");

        // TODO: make Docker container address configurable and put it into Ansible deployment
        return restTemplate.postForEntity(url, weather, GeneralOutlook.class).getBody();
    }

    @Override
    public byte[] getWeatherInformationPdf(String zip) {
        LOG.info("Call weatherbackend");

        return restTemplate.getForObject(url, byte[].class, zip);
    }
}
