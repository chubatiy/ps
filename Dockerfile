# --- Stage 1: Build the app ---
FROM maven:3.9.4-eclipse-temurin-17 as build

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# --- Stage 2: Run the app ---
FROM openjdk:17.0.1-jdk-slim

VOLUME /tmp
COPY --from=build /app/target/ps-api-*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
