# Start from a Java 21 base image
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Build the app (skip tests to speed up build)
RUN ./mvnw clean package -DskipTests
RUN echo "ENV_VARS:" && env

# Run the app
CMD ["java", "-jar", "target/tinycare-0.0.1-SNAPSHOT.jar"]
