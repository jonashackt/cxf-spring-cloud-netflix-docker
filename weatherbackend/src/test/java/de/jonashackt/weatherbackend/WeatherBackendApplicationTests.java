package de.jonashackt.weatherbackend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.jonashackt.businesslogic.IncredibleLogic;
import de.jonashackt.internalmodel.GeneralOutlook;
import de.jonashackt.internalmodel.Product;
import de.jonashackt.internalmodel.Weather;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        classes = WeatherBackendApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {"server.port=8080"}
)
public class WeatherBackendApplicationTests {
  
	@Test
    public void testWithRestAssured() {
	    
	    Weather weather = new Weather();
	    weather.setFlagColor("blue");
	    weather.setPostalCode("99425");
	    weather.setProduct(Product.ForecastBasic);
	    
	    
	    
	    given() // can be ommited when GET only
	        .contentType(ContentType.JSON)
            .body(weather)
        .when() // can be ommited when GET only
            .post("http://localhost:8080/weather/general/outlook")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .assertThat()
                .equals(IncredibleLogic.generateGeneralOutlook());
	    
	    GeneralOutlook outlook = given() // can be ommited when GET only
	            .contentType(ContentType.JSON)
	            .body(weather).post("http://localhost:8080/weather/general/outlook").as(GeneralOutlook.class);
	    
	    assertEquals("Weimar", outlook.getCity());
    }

	@Test
	public void testWithUniRest() throws Exception {

	    httpGetWithSimpleUrlParameter();

        httpPostWithObjectInAndOutMapping();
	}

    private void httpGetWithSimpleUrlParameter() throws UnirestException {
        String name = "Paul";

        HttpResponse<String> greeting = Unirest.get("http://localhost:8080/weather/{name}").header("accept", "text/plain").routeParam("name", name).asObject(String.class);

        assertThat(greeting.getBody(), containsString(" This is a RESTful HttpService written in Spring"));
    }

    private void httpPostWithObjectInAndOutMapping() throws com.mashape.unirest.http.exceptions.UnirestException {
        Weather weather = new Weather();
        weather.setFlagColor("blue");
        weather.setPostalCode("99425");
        weather.setProduct(Product.ForecastBasic);

        HttpResponse<GeneralOutlook> generalOutlookHttpResponse = Unirest.post("http://localhost:8080/weather/general/outlook")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(weather)
                .asObject(GeneralOutlook.class);

        assertEquals("Weimar", generalOutlookHttpResponse.getBody().getCity());
    }

    @Before
    public void setUp() {
        // Configure your wanted ObjectMapper, here we take jackson :)
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}
