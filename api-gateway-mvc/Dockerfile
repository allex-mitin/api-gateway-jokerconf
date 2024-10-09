FROM openjdk:21-jdk
WORKDIR /app
COPY build/libs/api-gateway-mvc-*.jar api-gateway-mvc.jar
EXPOSE 8080
CMD java $JAVA_PARAMS_APP -Dfile.encoding=UTF8 -jar api-gateway-mvc.jar
