import io.gatling.gradle.GatlingRunTask

plugins {
    id("java")
    id("io.gatling.gradle") version "3.11.5.2"
}

version = "1.0"
description = "gatling-tests"

dependencies{
    implementation("io.gatling:gatling-app:3.11.5")
//    implementation("io.gatling.highcharts:gatling-charts-highcharts:3.11.5")
}

gatling{

}

tasks.register<GatlingRunTask>("SlowBackendSimulation"){
    dependsOn("build")
    group = "GatlingTest"
    description = "Run gatling simulation for slow backend"
    simulationClassName = "com.jokerconf.gatling.SlowBackendSimulation"
}

tasks.register<GatlingRunTask>("FastBackendSimulation"){
    dependsOn("build")
    group = "GatlingTest"
    description = "Run gatling simulation for fast backend"
    simulationClassName = "com.jokerconf.gatling.FastBackendSimulation"
    jvmArgs = listOf("-Xmx2048m")
    systemProperties

}

tasks.register<GatlingRunTask>("ApiGatewaySlowBackendSimulation"){
    dependsOn("build")
    group = "GatlingTest"
    description = "Run gatling simulation for api gateway slow backend"
    simulationClassName = "com.jokerconf.gatling.ApiGatewaySlowBackendSimulation"
}

tasks.register<GatlingRunTask>("ApiGatewayFastBackendSimulation"){
    dependsOn("build")
    group = "GatlingTest"
    description = "Run gatling simulation for api gateway fast backend"
    simulationClassName = "com.jokerconf.gatling.ApiGatewayFastBackendSimulation"
}
