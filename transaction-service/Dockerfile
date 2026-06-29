# Build the whole reactor so shared-kernel is available, then keep only this jar.
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml .
COPY shared-kernel shared-kernel
COPY transaction-service transaction-service
COPY consolidation-service consolidation-service
RUN mvn -q -pl transaction-service -am -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/transaction-service/target/transaction-service-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
