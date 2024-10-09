package com.jokerconf.gateway.mvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.client.*;

@SpringBootApplication
@Slf4j
public class Application {

    public static void main(String[] args) {
        var context = SpringApplication.run(Application.class, args);

        log.info("""
                        ===============================================
                        HttpClient: {}
                        Virtual Thread Enabled: {}
                        """,
                getClientName(context.getBean(ClientHttpRequestFactory.class)),
                context.getEnvironment().getProperty("spring.threads.virtual.enabled", Boolean.class)
        );
    }

    private static String getClientName(ClientHttpRequestFactory clientHttpRequestFactory) {
        return switch (clientHttpRequestFactory) {
            case HttpComponentsClientHttpRequestFactory apache -> "Apache Http Client";
            case JdkClientHttpRequestFactory jdk -> "JDK Client";
            case OkHttp3ClientHttpRequestFactory ok ->  "Ok Http Client";
            case SimpleClientHttpRequestFactory simple -> "Spring Simple Http Client";
            case null -> "";
            default -> clientHttpRequestFactory.getClass().getName();
        };
    }


//    @Bean
//    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//        return args -> {
//
//            System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//            String[] beanNames = ctx.getBeanDefinitionNames();
//            Arrays.sort(beanNames);
//            for (String beanName : beanNames) {
//                System.out.println(beanName);
//            }
//        };
//    }
}
