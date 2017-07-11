FROM java:8-jre
MAINTAINER Alex Soroka <lehaSVV2009@gmail.com>

COPY ./build/libs/api-0.1.0.jar api.jar
CMD ["java", "-Xmx200m", "-jar", "/api.jar"]

EXPOSE 8080