FROM openjdk:17-oracle
WORKDIR /app
COPY target/Hostel-0.0.1-SNAPSHOT.jar hostel.jar
#COPY apache-activemq-5.6.3 /opt/apache-activemq-5.15.12
ENTRYPOINT ["java", "-jar", "hostel.jar", "--spring.activemq.broker-url=tcp://activemq:61616"]
EXPOSE 8080