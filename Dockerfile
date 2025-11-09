# Etapa 1 - Build do projeto com Maven
FROM registry.access.redhat.com/ubi8/openjdk-17:1.18 AS build
WORKDIR /usr/src/app
COPY . .
RUN ./mvnw package -DskipTests -Dquarkus.package.type=fast-jar

# Etapa 2 - Imagem final de execução
FROM registry.access.redhat.com/ubi8/openjdk-17:1.18
WORKDIR /deployments
COPY --from=build /usr/src/app/target/quarkus-app/lib/ /deployments/lib/
COPY --from=build /usr/src/app/target/quarkus-app/*.jar /deployments/
COPY --from=build /usr/src/app/target/quarkus-app/app/ /deployments/app/
COPY --from=build /usr/src/app/target/quarkus-app/quarkus/ /deployments/quarkus/
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/deployments/quarkus-run.jar"]
