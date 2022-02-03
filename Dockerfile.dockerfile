FROM openjdk:11-jdk-slim
WORKDIR /src
COPY . /src
RUN apt-get update
RUN apt-get install -y dos2unix
RUN dos2unix gradlew

RUN bash gradlew jar

WORKDIR /run
RUN cp /src/build/libs/*.jar  /run/server.jar
RUN cp /src/data.db /run/data.db

EXPOSE 8080:8080

CMD java -jar /run/server.jar


