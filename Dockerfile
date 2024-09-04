# PHASE 1 - Download & Install JDK
FROM ghcr.io/graalvm/jdk-community:21 as build
WORKDIR app
ADD ./build/libs/advanced_jpa-1.0.jar /app/
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev","/app/advanced_jpa-1.0.jar"]
