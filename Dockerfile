FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar Restaurant.jar
ENTRYPOINT ["java","-jar","Restaurant.jar"]
EXPOSE 8080



