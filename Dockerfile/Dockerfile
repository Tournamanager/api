# Dockerfile

FROM adoptopenjdk/openjdk13:jdk-13_33

MAINTAINER Michiel Langen

RUN echo "deb http://archive.ubuntu.com/ubuntu trusty main universe" > /etc/apt/sources.list

RUN apt-get -y update

RUN DEBIAN_FRONTEND=noninteractive apt-get install -y -q python-software-properties software-properties-common