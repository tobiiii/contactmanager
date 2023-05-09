# Use a base image
FROM openjdk:11-jre-slim

# Set the working directory
WORKDIR /app

# Copy the application jar file and any additional dependencies
COPY target/my-spring-boot-app.jar /app/
COPY lib/*.jar /app/lib/

# Set the application name and version as environment variables
ENV APP_NAME=my-spring-boot-app
ENV APP_VERSION=1.0.0

# Expose the port the application listens on
EXPOSE 8080

# Start the application
CMD ["java", "-jar", "/app/my-spring-boot-app.jar"]
