# ---------- BUILD STAGE ----------
FROM gradle:jdk21 AS build
WORKDIR /app
COPY --chown=gradle:gradle . /app/
RUN gradle clean build -x test

# ---------- RUNTIME STAGE ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

# Wait a bit for DB (simple + effective for dev)
ENTRYPOINT ["sh", "-c", "sleep 10 && java -jar app.jar"]
