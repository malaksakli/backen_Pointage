FROM maven:3.8.3-openjdk-17 AS build

# Copier les sources et le fichier POM dans le conteneur
COPY src /home/app/src
COPY pom.xml /home/app

# Construire l'application avec Maven
RUN mvn -f /home/app/pom.xml clean package -DskipTests

# Exposer le port 8080 (utilisé par l'application Spring Boot)
EXPOSE 8080

# Commande pour exécuter le fichier JAR généré
ENTRYPOINT ["java", "-jar", "/home/app/target/Blacher-1.0.jar"]
