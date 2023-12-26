#!/bin/bash

GRADLE_VERSION=${1:-8.5}
dir=$(cd `dirname $0`;pwd)

SRC_DIR="${dir}/.tmp/gradleWrapper"
rm -fr "${SRC_DIR}"
mkdir -p "${SRC_DIR}"
touch "${SRC_DIR}/settings.gradle"
cd "${SRC_DIR}"
gradle wrapper --gradle-version "${GRADLE_VERSION}" --distribution-type all

for gradlewFile in $(find "${dir}" -type f -name gradlew | grep -v "${SRC_DIR}") ; do
    TARGET_DIR=$(dirname ${gradlewFile})
    echo "updating: ${TARGET_DIR}"
    cp "${SRC_DIR}/gradlew" "${TARGET_DIR}"
    cp "${SRC_DIR}/gradlew.bat" "${TARGET_DIR}"
    cp -r "${SRC_DIR}/gradle" "${TARGET_DIR}"
done
