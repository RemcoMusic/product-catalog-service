FROM eclipse-temurin:25-jre-alpine

COPY /target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]