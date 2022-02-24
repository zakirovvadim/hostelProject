FROM openjdk:17-oracle
WORKDIR /app
COPY target/Hostel-0.0.1-SNAPSHOT.jar hostel.jar
CMD ["java", "-jar", "hostel.jar"]