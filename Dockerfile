# Use Maven to build the application
FROM maven:3.8.4-openjdk:17-alpine AS builder
# Set the working directory
WORKDIR /build
# Copy the Maven project file
COPY pom.xml .
# Download the project dependencies
RUN mvn dependency:go-offline -B
# Copy the application source code
COPY src ./src
# Build the application
RUN mvn package -DskipTests
# Use JDK 17 as the base image
FROM openjdk:17-alpine
# Set the working directory
WORKDIR /app
# Copy the built JAR file from the builder stage
COPY --from=builder /build/target/contactmanager-0.0.1-SNAPSHOT.jar /app/contactmanager.jar
# Expose the port the application listens on
EXPOSE 8080
# Start the application
CMD ["java", "-jar", "contactmanager.jar"]
