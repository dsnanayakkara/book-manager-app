FROM openjdk:17-jdk-alpine

WORKDIR /app

# Copy wait-for script
#COPY wait-for-script.sh /wait-for-script.sh
#RUN chmod +x /wait-for-script.sh

# Copy Maven Wrapper and configuration
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Copy source files
COPY src ./src

# Make the mvnw script executable
RUN chmod +x ./mvnw

# Use the Maven Wrapper for the package command
RUN ./mvnw clean package spring-boot:repackage -DskipTests

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/book-manager-app-0.0.1-SNAPSHOT.jar"]
