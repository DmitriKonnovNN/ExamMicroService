FROM openjdk:12-oracle
MAINTAINER Dmitrii Konnov dmitri.v.konnov@dmitrikonnov.solutions
EXPOSE 8080
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
