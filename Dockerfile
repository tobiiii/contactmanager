# Use OpenJDK 17 Alpine as the base image
FROM openjdk:17-alpine

# Set the working directory
WORKDIR /app

# Copy the application jar file to the container
COPY target/contactmanager-0.0.1-SNAPSHOT.jar /app/contactmanager.jar

# Expose the port the application listens on
EXPOSE 8080

# Start the application
CMD ["java", "-jar", "contactmanager.jar"]

