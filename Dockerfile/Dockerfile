FROM alpine:latest

RUN apk update \
    && apk add --no-cache texlive-full

RUN echo "http://dl-cdn.alpinelinux.org/alpine/edge/community" >> /etc/apk/repositories

RUN apk add --no-cache openjdk17 maven

WORKDIR /spring-app
COPY SpringBooTex .
RUN mvn clean install

CMD mvn spring-boot:run

