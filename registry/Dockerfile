FROM java:8-jre
MAINTAINER Alex Soroka <lehaSVV2009@gmail.com>

COPY ./build/libs/registry-0.1.0.jar registry.jar
CMD ["java", "-Xmx200m", "-jar", "/registry.jar"]

EXPOSE 9999