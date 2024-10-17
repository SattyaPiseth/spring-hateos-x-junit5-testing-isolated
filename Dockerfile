# PHASE 1 - Download & Install JDK
FROM ghcr.io/graalvm/jdk-community:21 AS build
WORKDIR app
ADD . /app/
RUN ./gradlew clean build

FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=stage","app.jar"]
