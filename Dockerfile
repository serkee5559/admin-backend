# 1. 빌드 스테이지 (Gradle 8.11로 버전 업)
FROM gradle:8.11-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon -x test

# 2. 실행 스테이지
FROM amazoncorretto:17-alpine
EXPOSE 8080
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]