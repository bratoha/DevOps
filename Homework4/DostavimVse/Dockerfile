FROM maven:3.8.4-jdk-8-slim

ADD . /app
WORKDIR /app

RUN mvn clean install -Dstart-class=Main && \
    cp /app/target/dostavimvse-0.0.1-SNAPSHOT.jar Main.jar && \
    mvn clean install -Dstart-class=ru.fivt.dostavimvse.DostavimvseApplication && \
    cp /app/target/dostavimvse-0.0.1-SNAPSHOT.jar App.jar && \
    chmod -R 777 run.sh

# I have no idea why, but I can't execute run.sh, I tried as best I could
CMD java -jar Main.jar && java -jar App.jar
