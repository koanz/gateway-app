FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
ADD ./target/user-0.0.1-SNAPSHOT.jar user.jar

ENTRYPOINT [ "java", "-jar", "user.jar" ]