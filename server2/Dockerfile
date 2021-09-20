FROM openjdk:11-jdk-slim AS builder

WORKDIR /build

COPY . .

RUN chmod +x gradlew

RUN ./gradlew clean build
RUN cp build/libs/*.jar app.jar

FROM openjdk:11-jre-slim

WORKDIR /run

COPY --from=0 /build/app.jar .
RUN chmod +x app.jar
ENTRYPOINT ["java","-jar","app.jar"]
