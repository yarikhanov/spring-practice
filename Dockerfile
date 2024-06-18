FROM bellsoft/liberica-openjdk-centos:latest

ENV spring.datasource.url='jdbc:mysql://host.docker.internal:3306/yarikhanov'


COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]