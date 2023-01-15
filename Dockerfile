FROM openjdk:17-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} flight-booking-test.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/flight-booking-test.jar"]