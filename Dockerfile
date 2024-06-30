FROM openjdk:21-jdk

WORKDIR /app

COPY target/game-review-service-0.0.1-SNAPSHOT.jar /app/game-review-service-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/game-review-service-0.0.1-SNAPSHOT.jar"]