#!/bin/bash

APP=qh-wap
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

dataStr=$(date +%Y%m%d%H%M%S)

if [ -e ${DIR}/upload/*.war ] ; then
    for f in ${DIR}/upload/*.war
    do
        md5Str=$(md5sum ${f} | cut -f1 -d ' ')
        bakName="$(basename $f).${dataStr}.$md5Str"
        mv $f $DIR/bak/$bakName
    done
fi

scp root@test12.kingsilk.xyz:/root/upload/kingsilk/${APP}/${APP}*.war ${DIR}/upload/
