package de.jonashackt.adapter;

import de.jonashackt.internalmodel.GeneralOutlook;
import de.jonashackt.internalmodel.Weather;

public interface WeatherBackendAdapter {
    GeneralOutlook generateGeneralOutlook(Weather weather);

    byte[] getWeatherInformationPdf(String zip);
}
