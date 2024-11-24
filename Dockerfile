FROM eclipse-temurin:17-jdk-alpine
COPY target/*.jar Restaurant.jar
ENTRYPOINT ["java","-jar","Restaurant.jar"]
EXPOSE 8080



