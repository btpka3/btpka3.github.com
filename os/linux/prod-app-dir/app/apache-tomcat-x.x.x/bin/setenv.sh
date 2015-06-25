#!/bin/bash

. /etc/profile.d/test12.sh

export today=`date +%Y%m%d%H%M%S`

export CATALINA_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && cd .. && pwd )"
export CATALINA_PID=${CATALINA_HOME}/tomcat.pid
export CATALINA_OPTS=" \
    -server \
    -Xms512m \
    -Xmx1024m \
    -XX:PermSize=32m \
    -XX:MaxPermSize=256m \
    -Xss256k \
    -XX:ErrorFile=${CATALINA_HOME}/logs/start.at.${today}.hs_err_pid.log \
    -XX:+UseConcMarkSweepGC \
    -XX:+HeapDumpOnOutOfMemoryError \
    -XX:HeapDumpPath=${CATALINA_HOME}/logs/start.at.${today}.dump.hprof \
    -XX:+PrintGCDateStamps \
    -XX:+PrintGCDetails \
    -Xloggc:${CATALINA_HOME}/logs/start.at.${today}.gc.log \
    -Duser.timezone=GMT+08 \
    -Dfile.encoding=UTF-8 \
"
