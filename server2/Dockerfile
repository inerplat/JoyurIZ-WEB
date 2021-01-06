FROM openjdk:11-jdk-slim AS builder

WORKDIR /workspace/app

COPY . .

RUN chmod +x gradlew

RUN ./gradlew clean build
RUN cp build/libs/*.jar app.jar

FROM openjdk:11-jre-slim

COPY --from=0 /workspace/app/app.jar .
RUN chmod +x app.jar
ENTRYPOINT ["java","-jar","app.jar"]
