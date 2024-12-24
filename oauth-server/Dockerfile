FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
EXPOSE 9000
ADD ./target/oauth-server-0.0.1-SNAPSHOT.jar oauth-server.jar

ENTRYPOINT [ "java", "-jar", "oauth-server.jar" ]