FROM mysql:8.4.0

ENV MY_ROOT_USERNAME = root
ENV MY_ROOT_PASSWORD = root
ENV MYSQL_DATABASE = yarikhanov

FROM maven:3.9.4-eclipse-temurin-17 as  build

COPY . .

FROM bellsoft/liberica-openjdk-centos:latest

WORKDIR /app

COPY target/spring-practice-0.0.1-SNAPSHOT.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]