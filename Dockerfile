# Use Eclipse Temurin 17 as the base image (replaces deprecated OpenJDK)
FROM eclipse-temurin:17-jdk-jammy as build

# Install Maven
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage - Use Eclipse Temurin JRE
FROM eclipse-temurin:17-jre-jammy

# Set working directory
WORKDIR /app

# Create a non-root user for security
RUN groupadd -r appuser && useradd -r -g appuser appuser

# Copy the JAR file from build stage
COPY --from=build /app/target/*.jar app.jar

# Change ownership to appuser
RUN chown -R appuser:appuser /app
USER appuser

# Expose port (this will be overridden by Render's PORT env var)
EXPOSE 8080

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=prod

# Run the application with dynamic port binding for Render
ENTRYPOINT ["sh", "-c", "java -jar /app/app.jar --server.port=${PORT:-8080}"]