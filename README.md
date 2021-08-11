
## Kira Slack Bot
Kira's Slack channel to integrate and communicate with a Transmit app, that provides upcoming trams status for nearest stops. 

## How to execute test
Locally:
* Once the app server is started, exposed api end point `/bus/status` can be hit directly from postman
http://localhost:8080/bus/status 

* Use curl to execute:
GET /bus/status HTTP/1.1
Host: localhost:8080
Cache-Control: no-cache
Postman-Token: 0a56396c-e7f3-fcca-53f4-ab7b0b2006e6


Process #2
 * Deploy the app. 
 * Add app in Slack channel


## Framework
This is a Maven project and framework consist of following JARs:
  * Spring Boot - starter web
  * Spring Boot - starter test
  * Spring Boot - maven plugin 
  * Junit
  * Java 1.8+

### Structure
This project is your standard Spring Boot Java project. 

### Models
`com/transmitapp/kira/domain` represents the model domain objects. 
Models contains representation of entities containing informaiton about upcoming trams with timing and delay reason (if any).

### Properties
`/kira/src/main/resources/application.properties` is a properties file to store Slack Bot Token, Slack webhook Url, and downstream service for restbus info. 

### Tests
Mock test and Unit test can be found inside test directory - `com.transmitapp.kira.domain.BusInfo.TransmitContollerTest`
 
