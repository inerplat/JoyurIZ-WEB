FROM openjdk:11-jdk-slim AS build

WORKDIR /workspace/app

COPY . .

RUN chmod +x gradlew

RUN ./gradlew clean build

WORKDIR /workspace/app

COPY build/libs/*.jar app.jar

RUN chmod +x app.jar
ENTRYPOINT ["java","-jar","app.jar"]
