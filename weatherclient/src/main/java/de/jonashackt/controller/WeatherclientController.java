package de.jonashackt.controller;

import de.codecentric.namespace.weatherservice.WeatherException;
import de.codecentric.namespace.weatherservice.WeatherService;
import de.codecentric.namespace.weatherservice.general.ForecastRequest;
import de.codecentric.namespace.weatherservice.general.ForecastReturn;
import de.jonashackt.model.Forecast;
import de.jonashackt.model.Temperature;
import de.jonashackt.transformation.WeatherServiceInputTransformer;
import de.jonashackt.transformation.WeatherServiceOutputTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class WeatherclientController {

    @Autowired
    private WeatherService weatherServiceClient;

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/forecast/{zip}")
    @ResponseStatus(HttpStatus.OK)
    public Forecast forecast(@PathVariable("zip") String zip) throws WeatherException {

        ForecastRequest forecastRequest = WeatherServiceInputTransformer.generateRequest(zip);

        ForecastReturn cityForecastByZIP = weatherServiceClient.getCityForecastByZIP(forecastRequest);

        return WeatherServiceOutputTransformer.mapIntoForecast(cityForecastByZIP);
    }
}
