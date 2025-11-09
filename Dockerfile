# Etapa 1 - Build do projeto com Maven Wrapper
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app
COPY . .

# Dá permissão de execução ao mvnw (caso não tenha)
RUN chmod +x mvnw

# Compila o projeto Quarkus em modo fast-jar (gera target/quarkus-app)
RUN ./mvnw package -DskipTests -Dquarkus.package.type=fast-jar

# Etapa 2 - Imagem final apenas com o resultado da build
FROM eclipse-temurin:17

WORKDIR /deployments
COPY --from=build /app/target/quarkus-app/lib/ /deployments/lib/
COPY --from=build /app/target/quarkus-app/*.jar /deployments/
COPY --from=build /app/target/quarkus-app/app/ /deployments/app/
COPY --from=build /app/target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/deployments/quarkus-run.jar"]
