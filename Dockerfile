FROM openjdk:17-jre-slim
VOLUME /tmp
COPY build/libs/ps-api-*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]