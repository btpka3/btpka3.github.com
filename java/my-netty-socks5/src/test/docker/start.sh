#!/bin/sh

today=`date +%Y%m%d%H%M%S`
DIR=/my-netty-socks5

#    -Xms256m \
#    -Xmx512m \
#    -Xss256k \
cd ${DIR}/src/test/docker/work

#apk add --no-cache curl

java \
    -server \
    -XX:MaxRAM=300m \
    -XX:ErrorFile=${DIR}/build/start.at.${today}.hs_err_pid.log \
    -XX:+UseConcMarkSweepGC \
    -XX:+HeapDumpOnOutOfMemoryError \
    -XX:HeapDumpPath=${DIR}/build//start.at.${today}.dump.hprof \
    -XX:+PrintGCDateStamps \
    -XX:+PrintGCDetails \
    -Xloggc:${DIR}/build/start.at.${today}.gc.log \
    -Duser.timezone=GMT+08 \
    -Dfile.encoding=UTF-8 \
    -Dspring.profiles.active=base,prod \
    -jar ${DIR}/build/libs/my-netty-socks5-0.1.0-SNAPSHOT-boot.jar

    #-Dlogging.config=${DIR}/src/test/docker/work/config/logback-spring.xml \


