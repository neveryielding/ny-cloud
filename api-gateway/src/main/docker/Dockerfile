FROM frolvlad/alpine-oraclejdk8
VOLUME /tmp
ADD api-gateway-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]