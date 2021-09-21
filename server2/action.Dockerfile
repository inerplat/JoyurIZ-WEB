FROM openjdk:11-jre-slim

WORKDIR /run

COPY run/app.jar .

ENTRYPOINT ["java","-jar","app.jar"]
