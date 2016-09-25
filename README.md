<img src="https://github.com/scott-seo/dvdstore-api/blob/master/images/api-spec.png">

# DVD Store API: API Design Demo

[![][travis img]][travis]
[![][license img]][license]

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

---

### Continuous Delivery
  * Feature flags
  * Dockerized

### Development
- Swagger UI





