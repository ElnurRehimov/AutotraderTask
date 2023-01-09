FROM alpine:3.11.2
RUN echo "Hello"
RUN apk add --no-cache openjdk11
COPY build/libs/autoTraderTask-0.0.1-SNAPSHOT.jar autoTraderTask-0.0.1-SNAPSHOT.jar
CMD java -jar  autoTraderTask-0.0.1-SNAPSHOT.jar





