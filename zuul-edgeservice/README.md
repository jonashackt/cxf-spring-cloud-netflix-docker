# zuul-edgeservice
Simple edge service using Spring Cloud Zuul

Using the [Spring Cloud Netflix stack](http://cloud.spring.io/spring-cloud-netflix/spring-cloud-netflix.html)

Fire up a backend service, like this one: https://github.com/jonashackt/weatherbackend and you don´t have to configure routes manually, this will be done for you with Eureka dynamically

If you use the weatherbackend and have

[http://localhost:8090/weather/general/outlook](http://localhost:8090/weather/general/outlook)

running with it, just try this edgeservice and type

[http://localhost:8080/api/weatherbackend/weather/general/outlook](http://localhost:8080/api/weatherbackend/weather/general/outlook)


See all dynamically configured routes here http://localhost:8080/routes
