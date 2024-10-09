package com.jokerconf.gatling;

import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.util.UUID;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class FastBackendSimulation extends Simulation {

     HttpProtocolBuilder httpProtocol =
            http.baseUrl("http://localhost:8181")
                    .acceptHeader("*/*")
                    .contentTypeHeader("application/json")
                    .shareConnections();


     ScenarioBuilder scn = scenario("Fast backend hello test")
            .exec(http("hello")
                    .get("/hello")
                    .queryParam("id", UUID.randomUUID().toString())
                    .requestTimeout(600000)
            );

     {
        setUp(
                scn.injectOpen(rampUsersPerSec(300).to(1200).during(30))
                        .protocols(httpProtocol)
        );
    }
}
