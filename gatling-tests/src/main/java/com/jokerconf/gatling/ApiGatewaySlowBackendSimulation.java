package com.jokerconf.gatling;

import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.OpenInjectionStep;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;
import java.util.UUID;

import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;

public class ApiGatewaySlowBackendSimulation extends Simulation {

     HttpProtocolBuilder httpProtocol =
            http.baseUrl("http://localhost:8180")
                    .acceptHeader("*/*")
                    .contentTypeHeader("application/json");


     ScenarioBuilder scn = scenario("Call slow backend with api gateway")
            .exec(http("hello")
                    .get("/slow-service/hello")
                    .queryParam("id", UUID.randomUUID().toString())
                    .requestTimeout(300)
            );

     {
        setUp(
                scn.injectClosed(CoreDsl.constantConcurrentUsers(600).during(1)).protocols(httpProtocol)
        );
    }
}
