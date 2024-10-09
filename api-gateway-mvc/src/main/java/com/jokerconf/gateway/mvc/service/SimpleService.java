package com.jokerconf.gateway.mvc.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Simple service fot test using Spring context in filters
 */
@Service
public class SimpleService {

    public String randomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
