FROM maven:3.8.2-jdk-8

WORKDIR /app
COPY . .

RUN mvn clean install -Dstart-class=Main && \
    cp /app/target/dostavimvse-0.0.1-SNAPSHOT.jar Main.jar && \
    mvn clean install -Dstart-class=ru.fivt.dostavimvse.DostavimvseApplication && \
    cp /app/target/dostavimvse-0.0.1-SNAPSHOT.jar App.jar && \

CMD ["run.sh"]
