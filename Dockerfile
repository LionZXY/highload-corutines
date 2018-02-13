FROM ubuntu:16.04

RUN apt-get -y  update

RUN apt-get install -y openjdk-8-jdk-headless

ENV WORK /hlkotlin
WORKDIR $WORK/

ADD . .
RUN ./gradlew build
EXPOSE 80

CMD java -jar build/libs/highload-corutines-1.0-SNAPSHOT.jar
