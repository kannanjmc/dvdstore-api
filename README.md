# DVD Store API: API Design Demo

[![license](https://img.shields.io/github/license/mashape/apistatus.svg?maxAge=2592000)](https://github.com/scott-seo/dvdstore-api/blob/master/LICENSE)

## Operational Concerns

#### Configuration
  * Credentials secured using S3 and EC2 profile
  * Dynamic config reloading
  * Pre-launch checks for environmental variables and configs 
  * Immutable infrastructure implemented using [Docker](https://hub.docker.com/r/scottseo/dvdstore-api/)
  <img src="https://github.com/scott-seo/dvdstore-api/blob/master/images/spacer2.png">
  <img src="https://github.com/scott-seo/dvdstore-api/blob/master/images/configuration.png">

#### Logging
  * Log forwarding to central logging service using logging agent [fluentd](https://hub.docker.com/r/scottseo/custom-fluentd/)
  * Logging to websocket for live tailing of logs
  * JSONized to be machine readable
  <img src="https://github.com/scott-seo/dvdstore-api/blob/master/images/loggly.png">

#### Metrics
  * Metrics collected by Graphite and rendered by Grafana
  <img src="https://github.com/scott-seo/dvdstore-api/blob/master/images/spacer2.png">
  <img src="https://github.com/scott-seo/dvdstore-api/blob/master/images/grafana2.png">

#### Traceability
  * Using Zipkin for distributed tracing 
  <img src="https://github.com/scott-seo/dvdstore-api/blob/master/images/spacer2.png">
  <img src="https://github.com/scott-seo/dvdstore-api/blob/master/images/zipkin2.png">

--

## Development Concerns

### Continuous Delivery
  * Achieved consistent build, test, deploy and ready to ship phases  
<img src="https://github.com/scott-seo/dvdstore-api/blob/master/images/pipeline3.png">

### Continuous Deployment 
  * Using feature flags to get features out to production as often and as soon as possible
  * Tracking every release and marking release events on Grafana graphs
  * Following [Etsy](https://codeascraft.com/2010/12/08/track-every-release/) deployment model  
  <img src="https://github.com/scott-seo/dvdstore-api/blob/master/images/togglz-main.png">
  <img src="https://github.com/scott-seo/dvdstore-api/blob/master/images/togglz-activation.png">

### API Design
  * API specification defined using swagger ([Open API Initiative](https://openapis.org/)) specification
  <img src="https://github.com/scott-seo/dvdstore-api/blob/master/images/spacer2.png">
  <img src="https://github.com/scott-seo/dvdstore-api/blob/master/images/api-spec.png"> 

### Testing
  * Unit tests, Integration tests 
  <img src="https://github.com/scott-seo/dvdstore-api/blob/master/images/spacer2.png">
  <img src="https://github.com/scott-seo/dvdstore-api/blob/master/images/codecoverage.png">
  * Postman Runner tests
  <img src="https://github.com/scott-seo/dvdstore-api/blob/master/images/postman_runner2.png">
  * Newman tests
  <img src="https://github.com/scott-seo/dvdstore-api/blob/master/images/spacer2.png">
  <img src="https://github.com/scott-seo/dvdstore-api/blob/master/images/newman_run2.png">
  
## Life is good!