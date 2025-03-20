FROM ubuntu:latest
LABEL authors="sanju"

FROM openjdk:22-jdk
ADD target/SpringAI.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/SpringAI.jar"]
