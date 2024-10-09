package com.jokerconf.gateway.mvc.predicate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gateway.server.mvc.common.Shortcut;
import org.springframework.cloud.gateway.server.mvc.predicate.PredicateSupplier;
import org.springframework.web.servlet.function.RequestPredicate;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomPredicateFunctions {

    @Shortcut
    public static RequestPredicate headerExist(String headerName) {
        return request -> request.headers().asHttpHeaders().containsKey(headerName);
    }

    public static class CustomPredicateSupplier implements PredicateSupplier {
        @Override
        public Collection<Method> get() {
            return Arrays.asList(CustomPredicateFunctions.class.getMethods());
        }
    }
}
