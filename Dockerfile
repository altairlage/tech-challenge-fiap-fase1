FROM openjdk:22-jdk

ARG JAR_FILE=target/*.jar
COPY ./sgr/target/sgr-0.0.1.jar app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar" ]