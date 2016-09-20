FROM anapsix/alpine-java:jre8

ENV LANG C.UTF-8

MAINTAINER  Scott Seo "scott.seo@gmail.com"

COPY ./dvdstore-api /opt/dvdstore-api/

COPY target/dvdstore-api.jar /opt/dvdstore-api/dist/dvdstore-api.jar

RUN chmod 755 /opt/dvdstore-api/bin/start.sh

CMD ["/opt/dvdstore-api/bin/start.sh"]

EXPOSE 8080 8081