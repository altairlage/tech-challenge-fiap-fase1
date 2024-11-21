FROM openjdk:21-jre

ARG JAR_FILE=targe/*.jar
COPY ./sgr/target/sgr-0.0.1.jar app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar" ]