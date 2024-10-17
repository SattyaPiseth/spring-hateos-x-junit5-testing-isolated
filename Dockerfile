# PHASE 1 - Download & Install JDK
# FROM ghcr.io/graalvm/jdk-community:21
# WORKDIR app
# ADD ./build/libs/advanced_jpa-1.0.jar /app/
# EXPOSE 8080
# ENTRYPOINT ["java","-jar","-Dspring.profiles.active=stage","/app/advanced_jpa-1.0.jar"]
------------------------------------------
# Build Stage
FROM ghcr.io/graalvm/jdk-community:21 AS build
WORKDIR /app
COPY . .
RUN ./gradlew clean build -x test

# Package Stage
FROM ghcr.io/graalvm/jdk-community:21 AS runtime
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

# Set the Spring profile (you can change 'your-profile' to the desired profile)
ENV SPRING_PROFILES_ACTIVE=stage

# Command to run the application with the specified profile
CMD ["java", "-jar", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "app.jar"]
