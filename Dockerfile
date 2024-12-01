# Utilise une image officielle OpenJDK
FROM openjdk:17-jdk-alpine

# Expose le port 8083 (ou celui utilisé par votre application Spring Boot)
EXPOSE 8083

# Ajoute le fichier généré à l'image Docker
# Assurez-vous que le fichier généré correspond au bon nom (dans votre cas, tpFoyer-17-0.0.1-SNAPSHOT.jar ou .war si un WAR est utilisé)
ADD target/tpFoyer-17-0.0.1-SNAPSHOT.jar app.jar

# Commande pour exécuter l'application Spring Boot
ENTRYPOINT ["java", "-jar", "/app.jar"]
