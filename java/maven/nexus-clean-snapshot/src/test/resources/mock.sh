#!/usr/bin/env bash

CUR_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

create_empty_file() {
    echo ":" $0
    mkdir -p `dirname $0`
    touch "$0"
}
export -f create_empty_file

DIR=/data0/nexus/sonatype-work/nexus/storage/snapshots/net/kingsilk

mkdir -p $DIR

cd $DIR

cat $CUR_DIR/mockFiles.txt | xargs -I {} bash -c create_empty_file  {}