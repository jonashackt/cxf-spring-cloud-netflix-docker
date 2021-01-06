cxf-spring-cloud-netflix-docker
======================================================================================
[![Build Status](https://github.com/jonashackt/cxf-spring-cloud-netflix-docker/workflows/github/badge.svg)](https://github.com/jonashackt/cxf-spring-cloud-netflix-docker/actions)
[![License](http://img.shields.io/:license-mit-blue.svg)](https://github.com/jonashackt/spring-boot-buildpack/blob/master/LICENSE)
[![renovateenabled](https://img.shields.io/badge/renovate-enabled-yellow)](https://renovatebot.com)

Spring Boot & Spring Cloud compatibility: https://spring.io/projects/spring-cloud

## Example project combining Spring Boot apps together with Spring Cloud Netflix &amp; Docker

It was created as a showcase for this blog post: [Scaling Spring Boot Apps on Docker Windows Containers with Ansible: A Complete Guide incl Spring Cloud Netflix and Docker Compose](https://blog.codecentric.de/en/2017/05/ansible-docker-windows-containers-scaling-spring-cloud-netflix-docker-compose/) and is used by this Ansible repository: [ansible-windows-docker-springboot](https://github.com/jonashackt/ansible-windows-docker-springboot)

It´s roughly structured like shown in this sketch:

![multiple-apps-spring-boot-cloud-netflix](https://blog.codecentric.de/files/2017/05/multiple-apps-spring-boot-cloud-netflix-768x543.png)

### Usage

As the whole example-application is Dockerized, just do a `docker-compose -f build.yml up && docker-compose up` and all apps will be started for you (the apps must be build first by Paketo). Run a `docker ps` to see what´s going on. Then enter http://localhost:8761/ to see all Services registering in Eureka.

The zuul-edgeservice proxies weatherservice (by retrieving routes dynamically from eureka-serviceregistry) that itself calls weatherbackend

Example: http://localhost:8080/api/weatherservice/soap

There´s a Client application inside this project too, so you can fire requests to the weather-service with that one to - and it should be clear, how to implement a consumer :) For that, just fire up the [weatherclient](https://github.com/jonashackt/cxf-spring-cloud-netflix-docker/tree/master/weatherclient) - it should be right there after a `mvn clean package` ran inside the root directoy.



### Build all apps using Docker-Compose & Paketo.io Cloud Native Buildpacks

The idea worked really long inside my head - it was inspired by this so question: https://stackoverflow.com/questions/65325129/execute-spring-buildpacks-when-calling-docker-compose-build-command

There's a separate Docker-Compose file `build.yml`, which contains a Compose service that is solely there to build all our Spring Boot apps using Paketo.io. 

Therefore all apps are mounted into the `paketo-build` service and the `mvn spring-boot:build-image` command does a Paketo build on every Maven submodule (which represent our Spring Boot apps), using the `spring-boot-maven-plugin`:

```yaml
version: '3.3'

services:

  paketo-build:
    image: maven:3.6-openjdk-15
    command: "mvn clean spring-boot:build-image -B -DskipTests --no-transfer-progress" # build all apps
    volumes:
      - "/var/run/docker.sock:/var/run/docker.sock:ro" # Mount Docker from host into build container for Paketo to work
      - "$HOME/.m2:/root/.m2" # Mount your local Maven repository into build container to prevent repeated downloads
      - "$PWD:/workspace" # Mount all Spring Boot apps into the build container
    working_dir: "/workspace"
```

Now our `docker-compose.yml` stays really simple - all services just use the Docker image tags produced by the Paketo build - like `weatherbackend:0.0.1-SNAPSHOT`:

```yaml
version: '3.3'

services:

  eureka-serviceregistry:
    image: eureka-serviceregistry:0.0.1-SNAPSHOT
    ports:
      - "8761:8761"
    tty:
      true
    restart:
      unless-stopped

  spring-boot-admin:
    image: spring-boot-admin:0.0.1-SNAPSHOT
    ports:
      - "8092:8092"
    environment:
      - REGISTRY_HOST=eureka-serviceregistry
    tty:
      true
    restart:
      unless-stopped

  # no portbinding here - the actual services should be accessible through Zuul proxy
  weatherbackend:
    image: weatherbackend:0.0.1-SNAPSHOT
    ports:
      - "8090"
    environment:
      - REGISTRY_HOST=eureka-serviceregistry
    tty:
      true
    restart:
      unless-stopped

  # no portbinding here - the actual services should be accessible through Zuul proxy
  weatherservice:
    image: weatherservice:0.0.1-SNAPSHOT
    ports:
      - "8095"
    environment:
      - REGISTRY_HOST=eureka-serviceregistry
    tty:
      true
    restart:
      unless-stopped

  zuul-edgeservice:
    image: zuul-edgeservice:0.0.1-SNAPSHOT
    ports:
      - "8080:8080"
    environment:
      - REGISTRY_HOST=eureka-serviceregistry
    tty:
      true
    restart:
      unless-stopped
```

To always issue a fresh build of all apps before running our apps, simply run:

```shell
docker-compose -f build.yml up && docker-compose up
```

Alternative, if we can rely on Maven on the host - we can leave out the first build container:


```shell
mvn clean spring-boot:build-image && docker-compose up
```



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