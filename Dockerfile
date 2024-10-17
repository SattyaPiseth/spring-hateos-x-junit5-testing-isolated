# PHASE 1 - Download & Install JDK
# FROM ghcr.io/graalvm/jdk-community:21
# WORKDIR app
# ADD ./build/libs/advanced_jpa-1.0.jar /app/
# EXPOSE 8080
# ENTRYPOINT ["java","-jar","-Dspring.profiles.active=stage","/app/advanced_jpa-1.0.jar"]
#------------------------------------------

# PHASE 1 - Build the application
FROM ghcr.io/graalvm/jdk-community:21 AS build
WORKDIR /app
COPY . .
RUN gradle build -x test

# PHASE 2 - Create the production image
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/advanced_jpa-1.0.jar /app/

# Add non-root user
# RUN addgroup --system appgroup && adduser --system appuser --ingroup appgroup
# USER appuser

EXPOSE 8080

# Set the entry point with production profile
ENTRYPOINT ["java", "-Dspring.profiles.active=stage", "-jar", "/app/advanced_jpa-1.0.jar"]

# Health check (optional)
# HEALTHCHECK CMD curl -f http://localhost:8080/actuator/health || exit 1

