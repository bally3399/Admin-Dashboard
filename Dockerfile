FROM maven:3-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -DskipTests
FROM eclipse-temurin:21-alpine
COPY --from=build /target/AdminBoard-0.1-SNAPSHOT.jar adminBoard.jar
ENTRYPOINT ["java","-Dspring.profiles.active=render","-jar","adminBoard.jar"]
EXPOSE 9191