FROM openjdk:8-jdk-alpine

WORKDIR /app
ADD build/libs/doctor-1.0-SNAPSHOT.jar doctor.jar
ADD tools tools
ADD db*.json .

EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java -Dserver.port=8080 -jar /app/doctor.jar" ]
