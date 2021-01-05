FROM openjdk:11-jdk-slim

WORKDIR /workspace/app

COPY . .

RUN ./gradlew clean build

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]
