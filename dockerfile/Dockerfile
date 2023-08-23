FROM openjdk:11
ARG JAR_FILE=api-gateway-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} myboot.jar
ENTRYPOINT ["java","-jar","/myboot.jar"]