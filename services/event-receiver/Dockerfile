FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app
COPY . .
RUN mvn -pl services/event-receiver -am clean package -DskipTests

FROM eclipse-temurin:21-jdk

COPY --from=build /app/services/event-receiver/target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]