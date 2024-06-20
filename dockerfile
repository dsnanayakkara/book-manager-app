FROM openjdk:17-jdk-alpine

WORKDIR /app

# Copy Maven Wrapper and configuration
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Make the mvnw script executable
RUN chmod +x ./mvnw

# Use the Maven Wrapper for the package command
RUN ./mvnw clean package -Dmaven.test.skip=true

COPY src ./src

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/book-manager-app-0.0.1-SNAPSHOT.jar"]
