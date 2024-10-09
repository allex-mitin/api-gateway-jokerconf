# Spring Cloud MVC API Gateway example

Example project of Spring Cloud Gateway. Shows undocumented features, bugs and other trips.

Project contains 3 modules:
## 1. api-gateway-mvc (Spring Cloud Gateway MVC Application)
Example of Gateway MVC contains multiple routes to backend service instances.
Examples of http requests see in. HttpExamples.http file.
You can configure env:
- `GATEWAY_HTTP_CLIENT_CONNECTION_TIMEOUT`
- `GATEWAY_HTTP_CLIENT_READ_TIMEOUT`
- `THREADS_VIRTUAL_ENABLED` toggle Java Virtual Threads
- `BACKEND_SERVICE_URL` url to instance of backend service (zero delay)
- `FAST_SERVICE_URL`  url to instance of backend service (not zero fast delay)
- `SLOW_SERVICE_URL`  url to instance of backend service (long delay)
- `HTTP_CLIENT_TYPE` type of http client (JDK, APACHE, SIMPLE)

## 2. backend-service (Proxied backend service)
Simple backend service with configured response delay.
All requests and response are logged with Logbook.
Configuration env:
- `TOMCAT_THREADS_MAX` tomcat threads pool size (default 200)
- `RESPONSE_DELAY` delay of response

## 3. gatling-tests (Hi-load gatling tests)

Hi load tests for Gateway. Tests running with gradle tasks.

# Build and run project

Build project with command 

    gradlew clean build

Then run with Docker 

    docker-compose up --build


| Container name       |            Description            | Port |
|:---------------------|:---------------------------------:|-----:|
| api-gateway-mvc      |   Spring Cloud Gateway MVC App    | 8180 |
| fast-backend-service |  Proxied backend with delay 50ms  | 8181 |
| slow-backend-service | Proxied backend with delay 5000ms | 8182 |
| backend-service      |   Proxied backend with no delay   | 8183 |
| grafana              |           Grafana image           | 3000 |
| prometheus           |         Prometheus image          | 9090 |



