plugins {
    id("java")
    id("org.springframework.boot") version "3.3.2"
}

version = "1.0.0"
description = "backend-service"


val springBootVersion = project.property("springBoot.version") as String
val lombokVersion = project.property("lombok.version") as String
val springDocVersion = project.property("springdoc.version") as String

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:${springBootVersion}"))
    implementation(platform("org.zalando:logbook-bom:3.9.0"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${springDocVersion}")
    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("org.zalando:logbook-spring-boot-starter")

    implementation("org.projectlombok:lombok:${lombokVersion}")
    annotationProcessor("org.projectlombok:lombok:${lombokVersion}")
}

