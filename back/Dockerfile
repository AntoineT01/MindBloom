# Étape 1 : Utilisation d'une image JDK pour builder l'application
FROM gradle:8.11.1-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle clean bootJar -x test

# Étape 2 : Utilisation d'une image légère JRE pour exécuter l'application
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
