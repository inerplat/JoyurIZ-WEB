FROM openjdk:11-jdk AS build

WORKDIR /workspace/app

COPY . .
RUN ./gradlew clean build

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]
