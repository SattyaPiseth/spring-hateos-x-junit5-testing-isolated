FROM gradle:jdk21 AS build
WORKDIR /app
COPY --chown=gradle:gradle . /app/

RUN gradle clean build -x test

FROM gradle:jdk21
WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=stage", "app.jar"]
