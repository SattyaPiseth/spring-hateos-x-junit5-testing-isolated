FROM gradle:7.5-jdk17 AS build
WORKDIR /app
COPY --chown=gradle:gradle . /app/

RUN gradle clean build

FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=stage", "app.jar"]
