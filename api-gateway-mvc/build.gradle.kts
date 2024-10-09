plugins {
    id("java")
    id("org.springframework.boot") version "3.3.2"
}

version = "1.0.0"
description = "api-gateway-mvc"


val springBootVersion = project.property("springBoot.version") as String
val springCloudVersion = project.property("springCloud.version") as String
val lombokVersion = project.property("lombok.version") as String
val springDocVersion = project.property("springdoc.version") as String

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:${springBootVersion}"))
    implementation(platform("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"))

    implementation("org.springframework.cloud:spring-cloud-starter-gateway-mvc")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${springDocVersion}")
    implementation("org.springframework.retry:spring-retry:2.0.8")

    implementation("org.apache.httpcomponents.client5:httpclient5")

    implementation("org.projectlombok:lombok:${lombokVersion}")
    annotationProcessor("org.projectlombok:lombok:${lombokVersion}")
}


tasks.compileJava{
    options.compilerArgs.add("-parameters")
}
