# Start from a Java 21 base image
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Build the app (skip tests to speed up build)
RUN ./mvnw clean package -DskipTests

# ✅ At runtime: print env vars, then run the app
CMD ["sh", "-c", "echo RUNTIME ENV_VARS: && env && java -jar target/tinycare-0.0.1-SNAPSHOT.jar"]
