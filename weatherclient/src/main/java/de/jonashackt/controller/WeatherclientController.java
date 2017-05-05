package de.jonashackt.controller;

import de.jonashackt.model.Forecast;
import de.jonashackt.model.Temperature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class WeatherclientController {

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/forecast/{zip}")
    @ResponseStatus(HttpStatus.OK)
    public Forecast forecast(@PathVariable("zip") String zip) {
        Forecast forecast = new Forecast();
        forecast.setCity("Weimar");
        forecast.setRainfallChance("100%");
        forecast.setTemperature(new Temperature("8ºC", "36ºC"));
        return forecast;
    }
}
