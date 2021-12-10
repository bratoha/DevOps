FROM maven:3.8.2-jdk-8

WORKDIR /app
COPY . .

RUN mvn clean install -Dstart-class=ru.fivt.dostavimvse.DostavimvseApplication

CMD ["java", "-jar", "/app/target/dostavimvse-0.0.1-SNAPSHOT.jar"]
