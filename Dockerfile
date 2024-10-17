# Build stage
FROM ghcr.io/graalvm/jdk-community:21 AS build
WORKDIR /app
ADD . /app/

RUN chmod +x ./gradlew
RUN ./gradlew clean build

# Production stage
FROM openjdk:21-jdk
WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=stage", "app.jar"]
