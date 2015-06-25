#!/bin/bash
. /etc/profile

######################################################### config

APP=qh-wap-front

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
BAK_DIR=${DIR}/bak
UPLOAD_DIR=${DIR}/upload
TAR=${APP}*.tar.gz
dataStr=$(date +%Y%m%d%H%M%S)

# 检查文件数量， 确保 upload 目录下只有一个最新的war包
checkFileCount(){
    file=$1                                                      
    fileCount=`ls $file |wc -l`
    [[ $fileCount -eq 0 ]] && {
        echo "$file not found, abort." 1>&2
        exit 1
    }
    [[ $fileCount -gt 1 ]] && {
        echo "$file is more than one, will back up and remove them, please run again." 1>&2
        
        for f in $file
        do
            md5Str=$(md5sum ${f} | cut -f1 -d ' ')
            bakName="$(basename $f).$md5Str"
            if [ ! -e $DIR/bak/$bakName ] ; the 
                mv $f $DIR/bak/$bakName
            fi
        done

        mv -f $file $BAK_DIR/
        exit 1
    }   
}

checkFileCount "${UPLOAD_DIR}/${TAR}"

#service tengine stop
#systemctl stop tengine

# 解压
cd "$UPLOAD_DIR"
rm -fr $APP
tar zxf $TAR

if [ -e $DIR/conf/* ] ; then
    cp -fr $DIR/conf/* $APP
fi

rm -fr $DIR/html/*
mv $APP/* $DIR/html
rmdir $APP
#service tengine start
#systemctl start tengine

