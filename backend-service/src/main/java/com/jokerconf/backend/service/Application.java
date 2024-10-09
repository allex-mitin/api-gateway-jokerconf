package com.jokerconf.backend.service;

import jakarta.validation.constraints.NotBlank;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Value("${delay:0}")
    private Long delay;

    @SneakyThrows
    @GetMapping("/hello")
    public HelloResponse getData(@RequestParam @NotBlank String id){
        Thread.sleep(delay);
        return new HelloResponse(id,  "OK", delay);
    }

    public record HelloResponse(String id, String status, long delay){}
}
