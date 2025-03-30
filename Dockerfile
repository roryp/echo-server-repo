FROM eclipse-temurin:21-jre-alpine

ARG JAR_FILE=target/echo-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
