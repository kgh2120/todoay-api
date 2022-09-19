FROM openjdk:20-ea-11-oraclelinux8
VOLUME /tmp
COPY build/libs/api-0.0.1-SNAPSHOT.jar api.jar
ENTRYPOINT ["java", "-jar", "api.jar"]