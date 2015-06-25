#!/bin/bash

APP=lizi-wap
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

#先备份
for f in ${DIR}/upload/*.war
do
   dataStr=$(date -d "`stat -c %y ${f}`" +%Y%m%d%H%M%S)
   backDir="${DIR}/bak/$(basename $f).${dataStr}.$RANDOM"
   mkdir $backDir
   mv $f $backDir
done

scp -P 2222 root@192.168.71.207:$DIR/upload/${APP}*.war ${DIR}/upload/

