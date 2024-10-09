package com.jokerconf.gateway.mvc.filter;

import com.jokerconf.gateway.mvc.service.SimpleService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gateway.server.mvc.common.Configurable;
import org.springframework.cloud.gateway.server.mvc.common.MvcUtils;
import org.springframework.cloud.gateway.server.mvc.common.Shortcut;
import org.springframework.cloud.gateway.server.mvc.filter.SimpleFilterSupplier;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.Arrays;
import java.util.stream.IntStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class CustomFilterFunctions {

    /**
     * Add header to request with random UUID value
     * Example of using simple args in filter initialization
     *
     * @param header - header name
     * @return
     */
    @Shortcut
    public static HandlerFilterFunction<ServerResponse, ServerResponse> addRequestHeaderUUID(String header) {
        return HandlerFilterFunction.ofRequestProcessor(request -> {
            var uuidService = MvcUtils.getApplicationContext(request).getBean(SimpleService.class);
            return ServerRequest.from(request)
                    .header(header, uuidService.randomUUID()).build();
        });
    }

    /**
     * Add header to request with random UUID value
     * Example of using list args in filter initialization
     *
     * @param headers - header names
     * @return filter function
     */
    @Shortcut
    public static HandlerFilterFunction<ServerResponse, ServerResponse> addRequestHeadersUUID(String... headers) {
        return HandlerFilterFunction.ofRequestProcessor(request -> {
            var uuidService = MvcUtils.getApplicationContext(request).getBean(SimpleService.class);
            var builder = ServerRequest.from(request);
            Arrays.stream(headers).forEach(header -> builder.header(header, uuidService.randomUUID()));
            return builder.build();
        });
    }

    /**
     * Add header to request with multiple random UUID values
     * Example of using object args in filter initialization
     *
     * @param config - filter configuration object
     * @return
     */
    @Shortcut
    @Configurable
    public static HandlerFilterFunction<ServerResponse, ServerResponse> addRequestHeaderUUIDValues(UuidFilterConfig config) {
        return HandlerFilterFunction.ofRequestProcessor(request -> {
            var uuidService = MvcUtils.getApplicationContext(request).getBean(SimpleService.class);
            return ServerRequest.from(request)
                    .header(
                            config.getHeader(),
                            IntStream.range(0, config.getCount())
                                    .mapToObj(i -> uuidService.randomUUID())
                                    .toArray(String[]::new)
                    ).build();
        });
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UuidFilterConfig {
        private String header;
        private int count;
    }


    static class FilterSupplier extends SimpleFilterSupplier {

        public FilterSupplier() {
            super(CustomFilterFunctions.class);
        }
    }
}
