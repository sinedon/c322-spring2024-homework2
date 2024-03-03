FROM eclipse-temurin:17
WORKDIR /home
COPY ./target/demo-0.0.1-SNAPSHOT.jar guitar-services.jar
ENTRYPOINT ["java", "-jar", "guitar-services.jar"]