FROM openjdk:11-jdk-slim AS build

WORKDIR /workspace/app

COPY . .

RUN chmod +x gradlew

RUN ./gradlew clean build

ARG JAR_FILE=/workspace/app/build/libs/*.jar
RUN chmod -R +rx /workspace/app/build/libs
COPY ${JAR_FILE} app.jar
RUN chmod +x app.jar
ENTRYPOINT ["java","-jar","app.jar"]
