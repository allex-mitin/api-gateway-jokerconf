package com.jokerconf.gatling;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.util.UUID;

import static io.gatling.javaapi.core.CoreDsl.rampUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;

public class ApiGatewayFastBackendSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol =
            http.baseUrl("http://localhost:8180")
                    .acceptHeader("*/*")
                    .contentTypeHeader("application/json")
                    .shareConnections();


    ScenarioBuilder scn = scenario("Call fast backend with api gateway")
            .exec(http("hello")
                    .get("/fast-service/hello")
                    .queryParam("id", UUID.randomUUID().toString())
                    .requestTimeout(300)
            );

    {
        setUp(
                scn.injectOpen(rampUsersPerSec(300).to(1200).during(30))
                        .protocols(httpProtocol)
        );
    }
}
