<img src="https://github.com/scott-seo/dvdstore-api/blob/master/images/api-spec.png">

# DVD Store API: API Design Demo

[![license](https://img.shields.io/github/license/mashape/apistatus.svg?maxAge=2592000)](https://github.com/scott-seo/dvdstore-api/blob/master/LICENSE)
[![Build Status](https://travis-ci.org/scott-seo/dvdstore-api.svg?branch=master)](https://travis-ci.org/scott-seo/dvdstore-api)

## DevOps Discipline

#### Configuration
  * Credentials secured using S3 and credentials authenticated using EC2 profile
  * Dynamic config reloading
  * Pre-launch checks for environmental variables and configs 
  * Dockerized [image](https://hub.docker.com/r/scottseo/dvdstore-api/)

#### Logging
  * Log forwarding using fleuntd [image](https://hub.docker.com/r/scottseo/dvdstore-db/)
  * Jsonized
  * Websocket for quick access

#### Metrics
  * Grafana backed by Graphite

#### Traceability
  * Use of Correlation ID in logging

---

## Development Discipline

### Continuous Deployment
  * Dark release using feature flags

### Development
  * API specification defined using swagger





