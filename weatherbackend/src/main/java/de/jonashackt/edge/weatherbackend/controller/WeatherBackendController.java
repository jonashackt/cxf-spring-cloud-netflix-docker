package de.jonashackt.edge.weatherbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.codecentric.soap.internalmodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;

import de.codecentric.soap.businesslogic.IncredibleLogic;

@RestController
@RequestMapping("/weather")
public class WeatherBackendController {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherBackendController.class);

    @RequestMapping(path = "/general/outlook", method=RequestMethod.POST, produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody GeneralOutlook generateGeneralOutlook(@RequestBody Weather weather) throws JsonProcessingException {
        LOG.info("Called Backend");
        /*
         * Some incredible Businesslogic...
         */
        return IncredibleLogic.generateGeneralOutlook();
    }

    @RequestMapping(path = "/general/outlook", method=RequestMethod.GET, produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody String infoAboutGeneralOutlook() throws JsonProcessingException {
        Weather weather = new Weather();
        weather.setFlagColor("blue");
        weather.setPostalCode("99425");
        weather.setUser(new User(55, 5634500, MethodOfPayment.Bitcoin));
        weather.setProduct(Product.ForecastBasic);

        ObjectMapper mapper = new ObjectMapper();
        String weatherJson = mapper.writeValueAsString(weather);

        return "Try a POST also against this URL! Just send some body with it like: '" + weatherJson + "'";
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = "text/plain")
    public String whatsTheSenseInThat(@PathVariable("name") String name) {
        return "Hello " + name + "! This is a RESTful HttpService written in Spring. Try to use some other HTTP verbs (don´t say 'methods' :P ) :)";
    }
}
