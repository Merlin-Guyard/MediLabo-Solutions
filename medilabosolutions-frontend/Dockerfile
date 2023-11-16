# Image de base pour construire l'image
FROM openjdk:21

# Répertoire de travail du conteneur docker
WORKDIR /app

# copie de .mvn, mvnw, pom.xml et src vers le répertoire de travail
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src

# Télécharge toutes les dépendances mvn dans le répertoire de travail
RUN ./mvnw dependency:resolve

# Commande par défaut lorsque le conteneur est lancé (wrappeur et commande mvn)
CMD ["./mvnw", "spring-boot:run"]