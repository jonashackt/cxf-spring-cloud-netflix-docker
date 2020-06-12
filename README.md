cxf-spring-cloud-netflix-docker
======================================================================================
[![Build Status](https://travis-ci.com/jonashackt/cxf-spring-cloud-netflix-docker.svg?branch=master)](https://travis-ci.com/jonashackt/cxf-spring-cloud-netflix-docker)

## Example project combining Spring Boot apps together with Spring Cloud Netflix &amp; Docker

It was created as a showcase for this blog post: [Scaling Spring Boot Apps on Docker Windows Containers with Ansible: A Complete Guide incl Spring Cloud Netflix and Docker Compose](https://blog.codecentric.de/en/2017/05/ansible-docker-windows-containers-scaling-spring-cloud-netflix-docker-compose/) and is used by this Ansible repository: [ansible-windows-docker-springboot](https://github.com/jonashackt/ansible-windows-docker-springboot)

It´s roughly structured like shown in this sketch:

![multiple-apps-spring-boot-cloud-netflix](https://blog.codecentric.de/files/2017/05/multiple-apps-spring-boot-cloud-netflix-768x543.png)

### Usage

As the whole example-application is Dockerized, just do a `docker-compose up -d` and all apps will be started for you. Run a `docker ps` to see what´s going on. Then enter http://localhost:8761/ to see all Services registering in Eureka.

The zuul-edgeservice proxies weatherservice (by retrieving routes dynamically from eureka-serviceregistry) that itself calls weatherbackend

Example: http://localhost:8080/api/weatherservice/soap

There´s a Client application inside this project too, so you can fire requests to the weather-service with that one to - and it should be clear, how to implement a consumer :) For that, just fire up the [weatherclient](https://github.com/jonashackt/cxf-spring-cloud-netflix-docker/tree/master/weatherclient) - it should be right there after a `mvn clean package` ran inside the root directoy.


### Spring Cloud 2.x Upgrade

Renamed starters: https://github.com/spring-projects/spring-cloud/wiki/Spring-Cloud-Edgware-Release-Notes

##### Errors bean overriding

```
***************************
APPLICATION FAILED TO START
***************************

Description:

The bean 'weatherServiceClient', defined in de.jonashackt.WeatherclientTestApplication, could not be registered. A bean with that name has already been defined in class path resource [de/jonashackt/configuration/WeatherclientConfiguration.class] and overriding is disabled.

```

See https://stackoverflow.com/questions/51367566/trouble-when-changing-spring-boot-version-from-2-0-3-release-to-2-1-0-build-snap, 
bean overriding (DI) isn't the default behavior anymore and you have to use:

```
spring.main.allow-bean-definition-overriding: true
```

inside `src/test/resources/application.yml`.

### Links

http://projects.spring.io/spring-cloud/

http://cloud.spring.io/spring-cloud-static/Dalston.RELEASE/

https://github.com/sqshq/PiggyMetrics

https://github.com/kbastani/spring-cloud-microservice-example