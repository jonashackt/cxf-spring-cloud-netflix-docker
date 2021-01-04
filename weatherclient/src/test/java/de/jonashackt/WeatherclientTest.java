package de.jonashackt;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;

@SpringBootTest(
		classes = WeatherclientTestApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class WeatherclientTest {

	@LocalServerPort
	int port;

	@BeforeEach
	public void init() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	@Test public void
	should_retrieve_forecast_from_weatherservice() {

		given()
			.contentType(ContentType.JSON)
			.pathParam("zip", "99425")
		.when()
			.get("/forecast/{zip}")
		.then()
			.statusCode(HttpStatus.SC_OK);
	}

}
