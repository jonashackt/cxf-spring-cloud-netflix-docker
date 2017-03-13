# zuul-edgeservice
Simple edge service using Spring Cloud Zuul

Using the [Spring Cloud Netflix stack](http://cloud.spring.io/spring-cloud-netflix/spring-cloud-netflix.html)

Fire up a backend service, like this one: https://github.com/jonashackt/weatherbackend and configure your routes inside the [application.properties](https://github.com/jonashackt/weatherbackend/blob/master/src/main/resources/application.properties).

If you use the weatherbackend and have

[http://localhost:8090/weather/general/outlook](http://localhost:8090/weather/general/outlook)

running with it, just try this edgeservice and type

[http://localhost:8080/api/weatherbackend/weather/general/outlook](http://localhost:8080/api/weatherbackend/weather/general/outlook)
