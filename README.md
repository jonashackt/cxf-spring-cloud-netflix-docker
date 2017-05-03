cxf-spring-cloud-netflix-docker
======================================================================================
[![Build Status](https://travis-ci.org/jonashackt/cxf-spring-cloud-netflix-docker.svg?branch=master)](https://travis-ci.org/jonashackt/cxf-spring-cloud-netflix-docker)

## Example project combining Spring Boot apps together with Spring Cloud Netflix &amp; Docker


zuul-edgeservice proxies weatherservice (by retrieving routes dynamically from eureka-serviceregistry) that itself calls weatherbackend

Example:

http://localhost:8080/api/weatherservice/soap


### Links

http://projects.spring.io/spring-cloud/

http://cloud.spring.io/spring-cloud-static/Dalston.RELEASE/

https://github.com/sqshq/PiggyMetrics

https://github.com/kbastani/spring-cloud-microservice-example