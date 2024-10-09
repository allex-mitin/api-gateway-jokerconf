package com.jokerconf.gatling;

import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.OpenInjectionStep;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;
import java.util.UUID;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class SlowBackendSimulation extends Simulation {

     HttpProtocolBuilder httpProtocol =
            http.baseUrl("http://localhost:8182")
                    .acceptHeader("*/*")
                    .contentTypeHeader("application/json")
                    .shareConnections();


     ScenarioBuilder scn = scenario("Slow backend hello test")
            .exec(http("hello")
                    .get("/hello")
                    .queryParam("id", UUID.randomUUID().toString())
                    .requestTimeout(600000)
            );

     {
        setUp(
                scn.injectClosed(CoreDsl.constantConcurrentUsers(600).during(1)).protocols(httpProtocol)
        );
    }
}
