#!/usr/bin/env bash

cd /opt

java -jar dvdstore-api/dist/dvdstore-api.jar server dvdstore-api/conf/dvdstore-api.yml

