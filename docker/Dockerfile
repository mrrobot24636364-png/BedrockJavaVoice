FROM eclipse-temurin:21-jre
WORKDIR /app
COPY voice-server/build/libs/voice-server-0.1.0-all.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar","8080","dev-token","32"]
