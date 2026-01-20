# -------------------------------------------------------------
# STAGE 1: Build the Application (The "Builder" Image)
# -------------------------------------------------------------
# We use the full JDK image to compile the code
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# 1. Copy maven wrapper and pom.xml first to leverage Docker cache
#    This speeds up re-builds by caching dependencies unless pom.xml changes
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# 2. Download dependencies (safe to fail if offline, but helps caching)
RUN ./mvnw dependency:resolve
# 3. Copy the actual source code
COPY src ./src

# 4. Build the JAR file (skip tests to save time/memory on free tier)
RUN ./mvnw clean package -DskipTests

# -------------------------------------------------------------
# STAGE 2: Run the Application (The "Final" Image)
# -------------------------------------------------------------
# We use the JRE image (smaller, no compiler) for production
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# 1. Create a non-root user for security (Best Practice)
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# 2. Copy only the built JAR from the 'builder' stage
#    Note: This assumes your final jar is in target/ and ends with .jar
COPY --from=builder /app/target/*.jar app.jar

# 3. Expose the port (Render sets the PORT env var, but this documents it)
EXPOSE 8080

# 4. The command to start the app
ENTRYPOINT ["java", "-jar", "app.jar"]
