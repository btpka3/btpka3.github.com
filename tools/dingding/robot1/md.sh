#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
TOKEN=xxx
DINGDING_ROBOT_URL=https://oapi.dingtalk.com/robot/send?access_token=${TOKEN}


curl -v \
-XPOST \
${DINGDING_ROBOT_URL} \
-d @${DIR}/md.json \
-H "Content-Type: application/json" \
-H 'Pragma: no-cache' \
-H 'Accept-Encoding: gzip, deflate' \
-H 'Accept-Language: zh,en;q=0.9' \
-H 'csrfToken: VV13186ucEi3PrTXgR6iI7' \
-H 'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36' \
-H 'Accept: application/json, text/plain, */*' \
-H 'Cache-Control: no-cache' \
-H 'Connection: keep-alive' \
--compressed
