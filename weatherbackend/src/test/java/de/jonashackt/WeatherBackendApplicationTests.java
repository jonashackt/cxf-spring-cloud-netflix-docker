package de.jonashackt;

import de.jonashackt.businesslogic.IncredibleLogic;
import de.jonashackt.model.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
        classes = WeatherBackendApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class WeatherBackendApplicationTests {

    @LocalServerPort
    int port;

    @BeforeEach
    public void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

	@Test
    public void testWithRestAssured() {
	    
	    Weather weather = new Weather();
	    weather.setFlagColor("blue");
	    weather.setPostalCode("99425");
	    weather.setProduct(Product.ForecastBasic);
        weather.addUser(new User(27, 4300, MethodOfPayment.Bitcoin));
        weather.addUser(new User(45, 500300, MethodOfPayment.Paypal));
        weather.addUser(new User(67, 60000300, MethodOfPayment.Paypal));

	    given() // can be ommited when GET only
	        .contentType(ContentType.JSON)
            .body(weather)
        .when() // can be ommited when GET only
            .post("/weather/general/outlook")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .assertThat()
                .equals(IncredibleLogic.generateGeneralOutlook());
	    
	    GeneralOutlook outlook = given() // can be ommited when GET only
	            .contentType(ContentType.JSON)
	            .body(weather).post("/weather/general/outlook").as(GeneralOutlook.class);
	    
	    assertEquals("Weimar", outlook.getCity());
    }

    @Test
    public void getWeatherInformationPdf_should_return_correct_Pdf() throws IOException {

        byte[] pdf = given()
                        .contentType(ContentType.JSON)
                        .pathParam("zip", "99425")
                    .when()
                        .get("/weather/general/outlook/{zip}")
                    .then()
                        .statusCode(HttpStatus.SC_OK).extract().asByteArray();

        String textInPdf = extractPdfText(pdf);
        assertThat(textInPdf, containsString("Weather in your city"));
        assertThat(textInPdf, containsString("Weimar"));
        assertThat(textInPdf, containsString("18.1"));
        assertThat(textInPdf, containsString("Wind Gentle Breeze 3.6 m/s"));
        assertThat(textInPdf, containsString("West-southwest"));
        assertThat(textInPdf, containsString("Cloudiness scattered clouds"));
        assertThat(textInPdf, containsString("Pressure 1018 hpa"));
        assertThat(textInPdf, containsString("Humidity 55 %"));
        assertThat(textInPdf, containsString("Sunrise 23:30"));
        assertThat(textInPdf, containsString("Sunset 13:1"));

    }

    /**
     * Extracts all the Text inside a Pdf
     */
    private static String extractPdfText(byte[] pdfData) throws IOException {
        PDDocument pdfDocument = PDDocument.load(new ByteArrayInputStream(pdfData));
        try {
            return new PDFTextStripper().getText(pdfDocument);
        } finally {
            pdfDocument.close();
        }
    }
}
