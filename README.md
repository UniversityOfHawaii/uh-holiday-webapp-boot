# OpenJDK Version Comparison

### Prerequisites

This project relies on having two other projects adjacent to this one:
* traefik
* swarm-monitoring

##### Configure Prometheus to scrape all three holiday versions:

Add the following to your swarm monitoring project:

../swarm-monitoring/swarm-volumes/swarm-monitoring/prometheus-data/prometheus-dynamic-config.yml
```yml
- targets: ['holiday-zulu:8080']
  labels:
    job: holiday-zulu
    application: holiday-zulu
    __scheme__: http
    __metrics_path__: /holiday/holiday-zulu/actuator/prometheus

- targets: ['holiday-corretto:8080']
  labels:
    job: holiday-corretto
    application: holiday-corretto
    __scheme__: http
    __metrics_path__: /holiday/holiday-corretto/actuator/prometheus

- targets: ['holiday-adoptopenjdk-hotspot:8080']
  labels:
    job: holiday-adoptopenjdk-hotspot
    application: holiday-adoptopenjdk-hotspot
    __scheme__: http
    __metrics_path__: /holiday/holiday-adopt/actuator/prometheus

- targets: ['holiday-oracle:8080']
  labels:
    job: holiday-oracle
    application: holiday-oracle
    __scheme__: http
    __metrics_path__: /holiday/holiday-oracle/actuator/prometheus
```

### Developing

You can start a development environment for any of the jdks:

```shell
make zulu-shell
or
make corretto-shell
or
make adoptopenjdk-hotspot-shell
or
make oracle-shell
```

### Deploying locally

First, build the deployment images (which will run all the tests as well):

```shell
make build-deployment-images
```

Then, start traefik and the swarm-monitoring projects, followed by the holiday apps:

```shell
make start-traefik start-monitoring deploy
```

##### URLs:

**Holiday apps:**
* http://localhost/holiday/holiday-zulu
* http://localhost/holiday/holiday-corretto
* http://localhost/holiday/holiday-adopt

**Holiday Prometheus scrape:**
* http://localhost/holiday/holiday-zulu/actuator/prometheus
* http://localhost/holiday/holiday-corretto/actuator/prometheus
* http://localhost/holiday/holiday-adopt/actuator/prometheus

**Spring Boot Grafana Dashboard:**
* http://localhost/monitoring/grafana/d/lyoEhvPWk/spring-boot-statistics?orgId=1

**Prometheus targets:**
* http://localhost/monitoring/prometheus/targets

### Deploying to TEST

The project will build with each push according to the `Jenkinsfile`.

##### URLs:

**Holiday apps:**
* https://doctest.pvt.hawaii.edu/holiday/holiday-zulu
* https://doctest.pvt.hawaii.edu/holiday/holiday-corretto
* https://doctest.pvt.hawaii.edu/holiday/holiday-adopt

**Holiday Prometheus scrape:**
* https://doctest.pvt.hawaii.edu/holiday/holiday-zulu/actuator/prometheus
* https://doctest.pvt.hawaii.edu/holiday/holiday-corretto/actuator/prometheus
* https://doctest.pvt.hawaii.edu/holiday/holiday-adopt/actuator/prometheus

**Spring Boot Grafana Dashboard:**
* https://doctest.pvt.hawaii.edu/monitoring/grafana/d/lyoEhvPWk/spring-boot-statistics?orgId=1

**Prometheus targets:**
* https://doctest.pvt.hawaii.edu/monitoring/prometheus/targets

# Original README.md
A web application to display holidays used by UH.

[![Build Status](https://travis-ci.org/fduckart/uh-holiday-webapp-boot.png?branch=master)](https://travis-ci.org/fduckart/uh-holiday-webapp-boot)
[![Coverage Status](https://coveralls.io/repos/github/fduckart/uh-holiday-webapp-boot/badge.svg)](https://coveralls.io/github/fduckart/uh-holiday-webapp-boot)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d68ffad24e34410a9186edd61494a749)](https://www.codacy.com/app/fduckart/uh-holiday-webapp-boot?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=fduckart/uh-holiday-webapp-boot&amp;utm_campaign=Badge_Grade)

##### Java
You'll need a Java JDK to build and run the project (version 1.8).
If necessary, be sure to set your JAVA_HOME environment variable.

##### Building
Install the necessary project dependencies:

    $ ./mvnw install

To run the Application from the Command Line:

    $ ./mvnw clean spring-boot:run

To build a deployable war file for local development, if preferred:

    $ ./mvnw clean package

You should have a deployable war file in the target directory.
Deploy as usual in a servlet container, e.g. tomcat.

To run the Application, point your browser at:

http://localhost:8080/holiday/


##### Running Unit Tests
The project includes Unit Tests for various parts of the system.
For this project, Unit Tests are defined as those tests that will
rely on only the local development computer.
A development build of the application will run the Unit Tests.

To run the Unit Tests:

    $ ./mvnw clean test

To run a specific test class:

    $ ./mvnw clean test -Dtest=StringsTest

To run a single method in a test class:

    $ ./mvnw clean test -Dtest=StringsTest#trunctate
