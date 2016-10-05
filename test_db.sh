#!/usr/bin/env bash
set -x

DB_USER=$1
DB_PWD=$2
SRC_PWD=$3
SCHEMA=$4
SRC_HOST=db.scottwseo.com
TGT_HOST=dockerhost

DUMP_FILE=empty_schema.sql

docker rm -f postgres

docker run --name postgres -p 5432:5432 -e POSTGRES_USER=${DB_USER} -e POSTGRES_PASSWORD=${DB_PWD} -e POSTGRES_DB=${SCHEMA} -d postgres

export PGPASSWORD=${SRC_PWD}
pg_dump -s -O -h ${SRC_HOST} -U ${DB_USER} -n ${SCHEMA} ${SCHEMA} | grep -v Schema: | grep -v REVOKE | grep -v GRANT > ${DUMP_FILE}

sleep 3

export PGPASSWORD=${DB_PWD}
psql -h ${TGT_HOST} -U ${DB_USER} -f ${DUMP_FILE} ${SCHEMA}

psql -h ${TGT_HOST} -U ${DB_USER} ${SCHEMA} <<EOF
REVOKE ALL ON SCHEMA ${SCHEMA} FROM PUBLIC;
REVOKE ALL ON SCHEMA ${SCHEMA} FROM ${DB_USER};
GRANT ALL ON SCHEMA ${SCHEMA} TO ${DB_USER};
EOF

rm ${DUMP_FILE}