#!/bin/bash
. /etc/profile

######################################################### config

APP=qh-wap

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
BAK_DIR=${DIR}/bak
UPLOAD_DIR=${DIR}/upload
TOMCAT_DIR=${DIR}/apache-tomcat-8.0.23
WAR=${APP}*.war                        # 注意，含有通配符 '*' 的哦
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
           bakName="$(basename $f).${dataStr}.$md5Str"
           mv $f $DIR/bak/$bakName
        done

        mv -f $file $BAK_DIR/
        exit 1
    }   
}

checkFileCount "${UPLOAD_DIR}/${WAR}"

# 停止并清空tomcat
#service qh-wap stop
systemctl stop qh-wap
rm -fr $TOMCAT_DIR/logs/*
rm -fr $TOMCAT_DIR/work/*

# 解压
cd "$UPLOAD_DIR"
rm -fr ROOT
unzip -q -d ROOT $WAR 

if [ -e $DIR/conf/* ] ; then
    cp -fr $DIR/conf/* ROOT
fi

rm -fr $TOMCAT_DIR/webapps/ROOT
mv ROOT $TOMCAT_DIR/webapps/
#service qh-wap start
systemctl start qh-wap

