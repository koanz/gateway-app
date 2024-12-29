FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
ADD ./target/msvc-item-0.0.1-SNAPSHOT.jar item.jar

ENTRYPOINT [ "java", "-jar", "item.jar" ]