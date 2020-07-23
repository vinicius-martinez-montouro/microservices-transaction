FROM openjdk:jdk-11.0.8_10-alpine
VOLUME /tmp
ADD /target/transaction-gateway-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]