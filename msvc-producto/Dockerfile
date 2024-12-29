FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
ADD ./target/msvc-producto-0.0.1-SNAPSHOT.jar producto.jar

ENTRYPOINT [ "java", "-jar", "producto.jar" ]