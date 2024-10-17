# PHASE 1 - Download & Install JDK
# FROM ghcr.io/graalvm/jdk-community:21
# WORKDIR app
# ADD ./build/libs/advanced_jpa-1.0.jar /app/
# EXPOSE 8080
# ENTRYPOINT ["java","-jar","-Dspring.profiles.active=stage","/app/advanced_jpa-1.0.jar"]

# Use GraalVM JDK Community edition 21 as the base image
FROM ghcr.io/graalvm/jdk-community:21 as build

# Set the working directory in the container
WORKDIR /app

# Copy the gradlew wrapper
COPY gradlew .

# Make gradlew executable
RUN chmod 777 gradlew

# Build the application (this may still cause issues)
RUN ./gradlew clean build

# Alternative approach: copy compiled artifacts instead of building
# COPY target/*.jar app.jar

# Run your Java application
# CMD ["java", "-jar", "app.jar"]

