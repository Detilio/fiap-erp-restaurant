FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

ENV SPRING_DATASOURCE_URL jdbc:mysql://localhost:3306/mydb?useSSL=false

ENV SPRING_DATASOURCE_USERNAME ${MYSQL_USER}

ENV SPRING_DATASOURCE_PASSWORD ${MYSQL_PASSWORD}

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]