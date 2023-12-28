# Image de base pour construire l'image
FROM openjdk:21

# Répertoire de travail du conteneur docker
WORKDIR /app

COPY target/medilabosolutions-notes-0.0.1-SNAPSHOT.jar app.jar

# Commande par défaut lorsque le conteneur est lancé (wrappeur et commande mvn)
CMD ["java", "-jar", "app.jar"]