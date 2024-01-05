FROM openjdk:17-jdk-slim

VOLUME /tmp

EXPOSE 8083

COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]

