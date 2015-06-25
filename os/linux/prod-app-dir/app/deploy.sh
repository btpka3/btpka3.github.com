#!/bin/bash
. /etc/profile

#------------------------------------------------begin config

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
BAK_DIR=${DIR}/bak
APP=xxx-web
SUFFIX=.war
APP_ROOT_DIR=/data/app/${appName}/
TOMCAT_DIR=/usr/local/tomcat

#------------------------------------------------end config
cd "$DIR"

checkFileCount(){
    file=$1                                                      
    fileCount=`ls $file |wc -l`
    [[ $fileCount -eq 0 ]] && {
        echo "$file not found, abort." 1>&2
        exit 1
    }
    [[ $fileCount -gt 1 ]] && {
        echo "$file is more than one, will back up and remove them, please run again." 1>&2
        mv -f $file $BAK_DIR
        exit 1
    }   
}

checkFileCount "${DIR}/${APP}*${SUFFIX}"

service tomcat stop
rm -fr $TOMCAT_DIR/logs/*
rm -fr $TOMCAT_DIR/work/*

unzip $APP*$SUFFIX -d $APP
rm -fr $APP_ROOT_DIR/$APP*
mv $APP $APP_ROOT_DIR/$APP
cp -fr ./conf/$APP/* $APP_ROOT_DIR/$APP
service tomcat start

cd $DIR
mv -f $APP*$SUFFIX $BAK_DIR


