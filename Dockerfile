# Stage 1: Build the application using Maven
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
# Copy the pom.xml and source code into the container
COPY pom.xml .
COPY src ./src
RUN mvn dependency:go-offline
# Run the maven command to build the jar file
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime environment
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copy ONLY the built jar file from Stage 1 into Stage 2
COPY --from=build /app/target/*.jar app.jar
# Expose port 8080 for the Spring Boot app
EXPOSE 8080
# The command to start the application
CMD ["java", "-jar", "app.jar"]