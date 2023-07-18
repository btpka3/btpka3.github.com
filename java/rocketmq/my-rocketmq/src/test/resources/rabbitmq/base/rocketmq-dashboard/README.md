
Q: 为何不用 docker hub 中的 镜像

A: 因为这个镜像有比较大： 压缩后的：315.3 MB
而当前的 Dockerfile 是 copy 其构建语句，但换了基础镜像，压缩后的大小：146.465MB
https://hub.docker.com/r/apacherocketmq/rocketmq-dashboard/tags


```shell
# 构建本地 docker 镜像
podman build -t my-rocketmq-dashboard -f Dockerfile

# 本地试运行：（请修改对应nameserver的 ip 地址），然后浏览器访问： http://localhost:8084
podman run --rm -p 8084:8080 -e 'JAVA_OPTS=-Drocketmq.namesrv.addr=30.196.226.48:9876' localhost/my-rocketmq-dashboard:latest

# push 到 docker 镜像仓库
REG=registry.cn-hangzhou.aliyuncs.com
NAMESPACE=btpka3
USER=btpka3@163.com
podman login --username=${USER} ${REG}
podman push my-rocketmq-dashboard:latest ${REG}/${NAMESPACE}/rocketmq-dashboard:1.0.0
podman push my-rocketmq-dashboard:latest ${REG}/${NAMESPACE}/rocketmq-dashboard:latest
```
