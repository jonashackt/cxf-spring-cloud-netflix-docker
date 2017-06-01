cxf-spring-cloud-netflix-docker
======================================================================================
[![Build Status](https://travis-ci.org/jonashackt/cxf-spring-cloud-netflix-docker.svg?branch=master)](https://travis-ci.org/jonashackt/cxf-spring-cloud-netflix-docker)

## Example project combining Spring Boot apps together with Spring Cloud Netflix &amp; Docker

It was created as a showcase for this blog post: [Scaling Spring Boot Apps on Docker Windows Containers with Ansible: A Complete Guide incl Spring Cloud Netflix and Docker Compose](https://blog.codecentric.de/en/2017/05/ansible-docker-windows-containers-scaling-spring-cloud-netflix-docker-compose/) and is used by this Ansible repository: [ansible-windows-docker-springboot](https://github.com/jonashackt/ansible-windows-docker-springboot)

It´s roughly structured like shown in this sketch:

![multiple-apps-spring-boot-cloud-netflix](https://blog.codecentric.de/files/2017/05/multiple-apps-spring-boot-cloud-netflix-768x543.png)

### Usage

The zuul-edgeservice proxies weatherservice (by retrieving routes dynamically from eureka-serviceregistry) that itself calls weatherbackend

Example: http://localhost:8080/api/weatherservice/soap

There´s a Client application inside this project too, so you can fire requests to the weather-service with that one to - and it should be clear, how to implement a consumer :) For that, just fire up the [weatherclient](https://github.com/jonashackt/cxf-spring-cloud-netflix-docker/tree/master/weatherclient) - it should be right there after a `mvn clean package` ran inside the root directoy.


### Links

http://projects.spring.io/spring-cloud/

http://cloud.spring.io/spring-cloud-static/Dalston.RELEASE/

https://github.com/sqshq/PiggyMetrics

https://github.com/kbastani/spring-cloud-microservice-example