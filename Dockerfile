# PHASE 1 - Download & Install JDK
# FROM ghcr.io/graalvm/jdk-community:21
# WORKDIR app
# ADD ./build/libs/advanced_jpa-1.0.jar /app/
# EXPOSE 8080
# ENTRYPOINT ["java","-jar","-Dspring.profiles.active=stage","/app/advanced_jpa-1.0.jar"]

# Use GraalVM JDK Community edition 21 as the base image
FROM ghcr.io/graalvm/jdk-community:21

# Set the working directory in the container
WORKDIR /app

ENTRYPOINT ["ls","-a"]
