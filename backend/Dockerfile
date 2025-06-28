FROM eclipse-temurin:17-jdk-alpine

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o jar gerado para dentro do container
COPY target/*.jar app.jar

# Expõe a porta configurada no application.yml
EXPOSE 3000

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
