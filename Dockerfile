FROM openjdk:17-jdk-slim

COPY target/hackaton-fia-sub-0.0.6.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]