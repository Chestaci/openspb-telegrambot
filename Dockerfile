FROM adoptopenjdk/openjdk11:ubi
ARG JAR_FILE=target/*.jar
ENV BOT_NAME=openspb_bot
ENV BOT_TOKEN=5302638355:AAHZeRUMQKZ5jzQXMhUCrRBTFNX2F5-j3V0
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dbot.username=${BOT_NAME}", "-Dbot.token=${BOT_TOKEN}", "-jar", "/app.jar"]