FROM java:8-jre
MAINTAINER Alex Soroka <lehaSVV2009@gmail.com>

COPY ./build/libs/config-0.1.0.jar config.jar
CMD ["java", "-Xmx200m", "-jar", "/config.jar"]

# Allow other applications to wait for config app starting by depends_on docker-compose property
HEALTHCHECK --interval=10s --timeout=3s CMD curl -f http://localhost:8888/health || exit 1

EXPOSE 8888