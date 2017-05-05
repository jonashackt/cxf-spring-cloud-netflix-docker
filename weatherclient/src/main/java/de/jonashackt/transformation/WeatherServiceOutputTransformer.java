package de.jonashackt.transformation;

import de.codecentric.namespace.weatherservice.datatypes.Temp;
import de.codecentric.namespace.weatherservice.general.ForecastReturn;
import de.jonashackt.model.Forecast;
import de.jonashackt.model.Temperature;

public class WeatherServiceOutputTransformer {

    public static Forecast mapIntoForecast(ForecastReturn cityForecastByZIP) {
        Forecast forecast = new Forecast();
        forecast.setCity(cityForecastByZIP.getCity());
        de.codecentric.namespace.weatherservice.datatypes.Forecast serviceForecast = cityForecastByZIP.getForecastResult().getForecast().get(0);
        forecast.setRainfallChance(serviceForecast.getProbabilityOfPrecipiation().getDaytime());
        forecast.setTemperature(mapIntoTemperature(serviceForecast.getTemperatures()));
        return forecast;
    }

    private static Temperature mapIntoTemperature(Temp temperatures) {
        return new Temperature(temperatures.getMorningLow(), temperatures.getDaytimeHigh());
    }
}
