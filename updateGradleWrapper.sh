#!/bin/bash

#GRADLE_VERSION=${1:-8.13}
dir=$(cd `dirname $0`;pwd)

SRC_DIR="${dir}/.tmp/gradleWrapper"
#SRC_DIR="${dir}"
rm -fr "${SRC_DIR}"
mkdir -p "${SRC_DIR}"
touch "${SRC_DIR}/settings.gradle"
cd "${SRC_DIR}"
#gradle wrapper --gradle-version "${GRADLE_VERSION}" --distribution-type all

# 使用 阿里云的gradle镜像站
# 参考: https://developer.aliyun.com/mirror/gradle
gradle wrapper --gradle-distribution-url=https://mirrors.aliyun.com/gradle/distributions/v9.2.0/gradle-9.2.0-all.zip

for gradlewFile in $(find "${dir}" -mindepth 2 -name settings.gradle | grep -v "${SRC_DIR}") ; do
    TARGET_DIR=$(dirname ${gradlewFile})
    echo "updating: ${TARGET_DIR}"
    rm -f  "${TARGET_DIR}/gradlew"
    rm -f  "${TARGET_DIR}/gradlew.bat"
    rm -fr "${TARGET_DIR}/gradle"

    #ln -s "${SRC_DIR}/gradlew" "${TARGET_DIR}"
    #ln -s "${SRC_DIR}/gradlew.bat" "${TARGET_DIR}"
    #ln -s "${SRC_DIR}/gradle" "${TARGET_DIR}"
    cp "${SRC_DIR}/gradlew" "${TARGET_DIR}"
    cp "${SRC_DIR}/gradlew.bat" "${TARGET_DIR}"
    cp -r "${SRC_DIR}/gradle" "${TARGET_DIR}"
done
 
