#!/bin/bash

. /etc/rc.d/init.d/functions
DATE="date +%Y-%m-%d"
TIME="date +%Y-%m-%d.%H:%M:%S"

# config
BAK_DIR=/data/back/xxx
CUR_BAK=$BAK_DIR/`$TIME`
RUNNING_USER=postgres
BACKUP_COUNT=2
DB_NAME=xxx
DB_USER=xxx

if [ -x "/sbin/runuser" ]; then
  SU="/sbin/runuser -m -s /bin/sh"
else
  SU="/bin/su -m -s /bin/sh"
fi

echo `$TIME` ============================ back up $DB_NAME

# get existed backups (as Array)
dirList=(`find $BAK_DIR -maxdepth 1 -type d  ! \( -path '*/\.*' -o -path $BAK_DIR \) | sort`)

# delete old backups, only keep the last one
let loopEnd=${#dirList[@]}-$BACKUP_COUNT
for i in `seq 0 $loopEnd`
do
  echo `$TIME` deleteing ${dirList[i]}
  rm -fr ${dirList[i]} 2>&1  || {
    exit $?
  }
done

# bakup a new one
echo `$TIME` creating new backup dir
$SU $RUNNING_USER -c "mkdir $CUR_BAK 2>&1" || {
    rc=$?
    rm -fr ${CUR_BAK} 2>&1 
    exit $rc
}

T_NAME=(
  T_1
  T_2
  T_3
)
echo ${T_NAME[0]}
 echo ${T_NAME[1]}
for T in ${T_NAME[@]} 
do
echo `$TIME` backing up $DB_NAME.$T
$SU $RUNNING_USER -c "pg_dump -U ${DB_USER} -F t -b -E UTF8 -f ${CUR_BAK}/${DB_NAME}.$T.`$TIME`.tar -t $T ${DB_NAME} 2>&1" || {
    rc=$?
    rm -fr ${CUR_BAK} 2>&1 
    exit $rc
}
done


echo `$TIME` success.


