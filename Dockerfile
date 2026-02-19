FROM maven:3.9.9-amazoncorretto-21-alpine AS dependencies
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B

FROM maven:3.9.9-amazoncorretto-21-alpine AS build
WORKDIR /app
COPY --from=dependencies /root/.m2 /root/.m2
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests

FROM alpine/java:21-jre AS execution
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]