FROM openjdk:25-jdk-bullseye

WORKDIR /app
COPY /build/libs/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]