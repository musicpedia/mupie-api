FROM openjdk:17-alpine

ARG JAR_FILE=/build/libs/musicpedia-api-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} /musicpedia-api.jar

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod", "/musicpedia-api.jar"]