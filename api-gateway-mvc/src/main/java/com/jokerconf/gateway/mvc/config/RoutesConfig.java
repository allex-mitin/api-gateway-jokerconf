package com.jokerconf.gateway.mvc.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static com.jokerconf.gateway.mvc.filter.CustomFilterFunctions.*;
import static com.jokerconf.gateway.mvc.filter.ErrorFilterFunctions.handleErrorFilter;
import static com.jokerconf.gateway.mvc.filter.ErrorFilterFunctions.throwErrorFilter;
import static com.jokerconf.gateway.mvc.predicate.CustomPredicateFunctions.headerExist;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.rewritePath;
import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.addRequestHeader;
import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.addResponseHeader;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

@Configuration
@Slf4j
public class RoutesConfig {

    @Value("${slow-service-url}")
    private String slowServiceUrl;

    @Value("${fast-service-url}")
    private String fastServiceUrl;

    @Value("${backend-service-url}")
    private String backendServiceUrl;

    @Bean
    public RouterFunction<ServerResponse> slowServiceRouter() {
        return route("slow-service-route")
                .route(path("/slow-service/**"), http(slowServiceUrl))
                .before(rewritePath("/slow-service/(?<segment>.*)", "/${segment}"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> fastServiceRouter() {
        return route("fast-service-route")
                .route(path("/fast-service/**"), http(fastServiceUrl))
                .before(rewritePath("/fast-service/(?<segment>.*)", "/${segment}"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> backendServiceRouter() {
        //example of using nest routes. We have two nest routes for backend-service:
        // 1. swagger and actuator
        // 2. all other endpoints
        return route("backend-service-route")
                .before(rewritePath("/backend-service/(?<segment>.*)", "/${segment}"))
                .nest(
                        path("/backend-service/actuator", "/backend-service/v3/api-docs"),
                        builder -> builder.GET(path("/**"), http(backendServiceUrl))
                )
                .nest(
                        path("/backend-service/"),
                        builder -> builder.route(path("/**"), http(backendServiceUrl))
                                //add filter for all endpoints except swagger and actuator
                                .filter(addResponseHeader("X-Route-Nested-Response-Id", "BACKEND_ROUTE_NESTED_RESPONSE"))

                )
                //add filter for all endpoints backend-service
                .filter(addResponseHeader("X-Route-Request-Id","BACKEND_ROUTE_REQUEST"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> customFilterJavaConfigRouter() {
        // Example of using route with custom filters and predicates
        return route("custom-filter-java-config-route")
                .GET(path("/custom-java/hello").and(headerExist("X-Custom-Header")), http(backendServiceUrl))
                .before(rewritePath("/custom-java/hello", "/hello"))
                .filter(addRequestHeaderUUID("X-HEADER-1"))
                .filter(addRequestHeadersUUID("X-HEADER-21", "X-HEADER-22"))
                .filter(addRequestHeaderUUIDValues(new UuidFilterConfig("X-HEADER-3", 2)))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> onErrorRouter() {
        // This route added for test onError function
        return route("on-error-route")
                .GET(path("/on-error/hello"), http(backendServiceUrl))
                .before(rewritePath("/on-error/hello", "/hello"))
                .filter(throwErrorFilter())
                .onError(th -> true, (throwable, request) -> ServerResponse
                        .status(501)
                        .body("On error function response: " + throwable.toString()))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> errorHandlingRouter() {
        // Example of handling errors in filter
        return route("error-handling-route")
                .GET(path("/error-handling/hello"), http(backendServiceUrl))
                .before(rewritePath("/error-handling/hello", "/hello"))
                .filter(handleErrorFilter())
                .filter(throwErrorFilter())
                .build();

    }



}
