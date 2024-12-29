FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
ADD ./target/msvc-order-0.0.1-SNAPSHOT.jar order.jar

ENTRYPOINT [ "java", "-jar", "order.jar" ]