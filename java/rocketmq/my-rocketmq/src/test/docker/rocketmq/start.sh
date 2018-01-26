#!/bin/sh

/data0/soft/rocketmq/bin/mqnamesrv &
/data0/soft/rocketmq/bin/mqbroker -n localhost:9876
