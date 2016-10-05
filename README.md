# DVD Store API: API Design Demo

[![license](https://img.shields.io/github/license/mashape/apistatus.svg?maxAge=2592000)](https://github.com/scott-seo/dvdstore-api/blob/master/LICENSE)
[![Build Status](https://travis-ci.org/scott-seo/dvdstore-api.svg?branch=master)](https://travis-ci.org/scott-seo/dvdstore-api)

## Operational Concerns

#### Configuration
  * Credentials secured using S3 and EC2 profile
  * Dynamic config reloading
  * Pre-launch checks for environmental variables and configs 
  * Dockerized [image](https://hub.docker.com/r/scottseo/dvdstore-api/)
  <img src="https://github.com/scott-seo/dvdstore-api/blob/master/images/configuration.png">

#### Logging
  * Log forwarding using fleuntd [image](https://hub.docker.com/r/scottseo/custom-fluentd/)
  * JSONized
  * Websocket for quick access

#### Metrics
  * Grafana backed by Graphite

#### Traceability
  * Use of Correlation ID in logging

--

## Development Concerns

### Continuous Develivery
<img src="https://github.com/scott-seo/dvdstore-api/blob/master/images/pipeline2.png">

### Continuous Deployment 
  * Using feature flags to get features out to production as often as possible
  <img src="https://github.com/scott-seo/dvdstore-api/blob/master/images/togglz-main.png">
  <img src="https://github.com/scott-seo/dvdstore-api/blob/master/images/togglz-activation.png">

### API Design
  * API specification defined using swagger
  <img src="https://github.com/scott-seo/dvdstore-api/blob/master/images/api-spec.png"> 

### Testing
  * Unit tests
  * Functional tests
  * Postman Runner tests
  <img src="https://github.com/scott-seo/dvdstore-api/blob/master/images/postman_runner.png">
  * Newman tests as part of Jenkins build
  <img src="https://github.com/scott-seo/dvdstore-api/blob/master/images/newman_run.png">