FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
ADD ./target/cliente-0.0.1-SNAPSHOT.jar cliente.jar

ENTRYPOINT [ "java", "-jar", "cliente.jar" ]