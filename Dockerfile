FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
COPY . .
# Just build directly - simpler and less prone to hanging
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
