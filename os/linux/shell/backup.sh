#!/bin/bash$
LOG=/root/bak/bak.log$
BAK_DIR=/root/bak$
CUR_TIME="date +%Y-%m-%d.%H:%M:%S"$
CUR_BAK=$BAK_DIR/test.`$CUR_TIME`$
$
echo `$CUR_TIME` start back > $LOG$
$
# get existed bakups$
dirList=(`find $BAK_DIR -maxdepth 1 -type d  ! \( -path '*/\.*' -o -path $BAK_DIR \) | sort`)$
echo eee====${#dirList[@]}===$loopEnd$
$
# delete old backups$
let loopEnd=${#dirList[@]}-2$
for i in `seq 0 $loopEnd`$
do$
 echo `$CUR_TIME` remove backup ${dirList[i]} > $LOG$
 echo delete=$i==${dirList[i]}$
done$
$
# bakup a new one$
echo `$CUR_TIME` backup a new one  ${CUR_BAK} > $LOG$$
mkdir $CUR_BAK$
if [[ $? -eq 0 ]]$
then$
  echo `${CUR_TIME}` backup success | tee > $LOG$
else$
  echo `$CUR_TIME` backup fail | tee > $LOG$
  rm -fr ${CUR_BAK}$
fi
