# Build stage
FROM ghcr.io/graalvm/jdk-community:21 AS build
WORKDIR /app
COPY --chown=gradle:gradle . /app/
RUN gradle clean build

# Production stage
FROM openjdk:21-jdk
WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=stage", "app.jar"]
