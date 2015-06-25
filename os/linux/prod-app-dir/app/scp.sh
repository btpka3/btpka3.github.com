#!/bin/bash

APP=qh-wap
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

if [ -e ${DIR}/upload/*.war ] ; then
    for f in ${DIR}/upload/*.war
    do
        md5Str=$(md5sum ${f} | cut -f1 -d ' ')
        bakName="$(basename $f).$md5Str"
        if [ ! -e $DIR/bak/$bakName ] ; the
            mv $f $DIR/bak/$bakName
        fi
    done
fi

scp root@test12.kingsilk.xyz:/root/upload/kingsilk/${APP}/${APP}*.war ${DIR}/upload/
