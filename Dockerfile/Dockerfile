# Dockerfile

FROM adoptopenjdk/openjdk13:jdk-13_33

MAINTAINER Michiel Langen

RUN mkdir /opt/app
COPY japp.jar /opt/app
CMD ["java", "-jar", "/opt/app/japp.jar"]