# open-jdk:17-jre
# build it from the root context .
# docker build -t hizmarck-microservice/services:latest -f src/main/docker/services/Dockerfile .
# docker network create hizmarck-microservice-net
#
# [[registration-service]]
# docker run --name hizmarck-registration --hostname registration --network hizmarck-microservice-net -p 1111:1111 hizmarck-microservice/services:latest java -jar app.jar
#
# [[payment-service]]
# docker run --name hizmarck-payments --hostname payments --network hizmarck-microservice-net -p 2222:2222 hizmarck-microservice/services:latest java -jar app.jar payments  --registration.server.hostname=<reg server ip addr>

FROM openjdk:17-alpine
ADD target/microservice-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 1111
EXPOSE 2222
