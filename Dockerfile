# -------- Build stage --------
# Use Maven with JDK to build the Spring Boot fat JAR
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy Maven files first for caching
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Create src directory and copy your Java code
RUN mkdir -p src
COPY src ./src

# Build the project (skip tests for faster builds)
RUN ./mvnw -B -DskipTests package

# -------- Runtime stage --------
FROM eclipse-temurin:17-jre-jammy

# Create non-root user
RUN useradd -m springuser && mkdir /app
USER springuser
WORKDIR /app

# Copy the JAR from build stage
COPY --from=build /app/target/parkinglot-reservation-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# JVM options
ENV JAVA_OPTS="-Xms256m -Xmx512m -XX:+UseContainerSupport -Djava.security.egd=file:/dev/./urandom"

# Optional healthcheck (if you enable Spring Actuator)
HEALTHCHECK --interval=30s --timeout=5s --start-period=10s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Start the Spring Boot application
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
