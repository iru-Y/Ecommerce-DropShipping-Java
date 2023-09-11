FROM openjdk:17

COPY trizi.jar trizi.jar

CMD ["java", "-jar", "trizi.jar"]