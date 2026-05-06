FROM amazoncorretto:11
VOLUME /tmp
EXPOSE 8080
ADD target/stackoverflow-0.0.1-SNAPSHOT.jar stackoverflow.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/stackoverflow.jar"]
