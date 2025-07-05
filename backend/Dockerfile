# Stage 1: build
FROM maven:3.8.6-eclipse-temurin-17 as build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: runtime
FROM eclipse-temurin:17-jdk-alpine
# Define o diretório de trabalho dentro do container
WORKDIR /app
# Copia o jar gerado para dentro do container
COPY --from=build /app/target/*.jar app.jar
# Expõe a porta configurada no application.yml
EXPOSE 3000
# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
