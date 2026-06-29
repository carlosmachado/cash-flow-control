# Build the whole reactor so shared-kernel is available, then keep only this jar.
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml .
COPY shared-kernel shared-kernel
COPY transaction-service transaction-service
COPY consolidation-service consolidation-service
RUN mvn -q -pl consolidation-service -am -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/consolidation-service/target/consolidation-service-*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
