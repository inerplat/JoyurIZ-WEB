FROM openjdk:11-jdk-slim

WORKDIR /workspace/app

COPY . /workspace/app
RUN ./gradlew clean build

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]
