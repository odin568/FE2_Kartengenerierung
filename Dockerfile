FROM openjdk:15-alpine3.12 AS build
COPY . /home/gradle/src
WORKDIR /home/gradle/src
RUN ./gradlew build

FROM openjdk:15-alpine3.12
EXPOSE 8080
LABEL autoheal=true
RUN apk --no-cache add curl
RUN mkdir -p /screenshots
COPY --from=build /home/gradle/src/build/libs/*.jar /app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar" , "/app.jar"]