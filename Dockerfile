#FROM openjdk:17-oracle
#ARG JAR_FILE=target/Hostel-0.0.1-SNAPSHOT.jar
#WORKDIR /opt/app
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java", "-jar", "app.jar"]
FROM maven:3.8.4-openjdk-17
COPY ./ ./
RUN mvn clean package
CMD ["java", "-jar", "target/Hostel-0.0.1-SNAPSHOT.jar"]
