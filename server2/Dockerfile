FROM openjdk:11-jdk-slim AS build

WORKDIR /workspace/app

COPY . .

RUN chmod +x gradlew

RUN ./gradlew clean build

FROM openjdk:11-jre-slim

ARG JAR_FILE=build/libs/*.jar
RUN chmod -R +rx build/libs/
COPY ${JAR_FILE} app.jar
RUN chmod +x app.jar
ENTRYPOINT ["java","-jar","app.jar"]
