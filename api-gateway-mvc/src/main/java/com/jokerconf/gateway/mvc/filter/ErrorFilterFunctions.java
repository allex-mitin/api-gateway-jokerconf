package com.jokerconf.gateway.mvc.filter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gateway.server.mvc.filter.SimpleFilterSupplier;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ErrorFilterFunctions {

    /**
     * This filter always throw Exception
     *
     * @return
     */
    public static HandlerFilterFunction<ServerResponse, ServerResponse> throwErrorFilter() {
        return HandlerFilterFunction.ofRequestProcessor(request -> {
            throw new UnsupportedOperationException("Filter function throw exception");
        });
    }

    /**
     * This filter handling all errors and return response
     *
     * @return
     */
    public static HandlerFilterFunction<ServerResponse, ServerResponse> handleErrorFilter() {
        return HandlerFilterFunction.ofErrorHandler(
                t -> true,
                (throwable, serverRequest) -> ServerResponse
                        .status(418)
                        .body(String.format("Exception with message '%s' handling in filter", throwable.getMessage()))
        );
    }

    static class FilterSupplier extends SimpleFilterSupplier {

        public FilterSupplier() {
            super(ErrorFilterFunctions.class);
        }
    }

}
