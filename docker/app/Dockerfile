FROM mcr.microsoft.com/openjdk/jdk:21-distroless AS build

WORKDIR /build
COPY . /build

RUN ./mvnw package

FROM mcr.microsoft.com/openjdk/jdk:21-distroless

WORKDIR /app
COPY ./target/*.jar /app/run.jar

EXPOSE 8080

USER app
ENTRYPOINT ["java", "-jar", "/app/run.jar"]