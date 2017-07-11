FROM java:8-jre
MAINTAINER Alex Soroka <lehaSVV2009@gmail.com>

COPY ./build/libs/book-service-0.1.0.jar book-service.jar
CMD ["java", "-Xmx200m", "-jar", "/book-service.jar"]

EXPOSE 5000