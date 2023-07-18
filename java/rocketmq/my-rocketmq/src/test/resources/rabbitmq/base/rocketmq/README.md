

# 自定义 docker 基础镜像

```shell
git clone git@github.com:apache/rocketmq-docker.git
cd rocketmq-docker/image-build 

ROCKETMQ_VERSION=5.1.1
podman build --no-cache -f Dockerfile-alpine -t rocketmq:${ROCKETMQ_VERSION}-alpine --build-arg version=${ROCKETMQ_VERSION} .

# 如果先下载慢，可以修改 Dockerfile-alpine 中zip包的下载地址为阿里云镜像的地址
# https://archive.apache.org/dist/rocketmq/${version}/rocketmq-all-${version}-bin-release.zip
# => 
# https://mirrors.aliyun.com/apache/rocketmq/${version}/rocketmq-all-${version}-bin-release.zip

# push 到 docker 镜像仓库
REG=registry.cn-hangzhou.aliyuncs.com
NAMESPACE=btpka3
USER=btpka3@163.com
podman login --username=${USER} ${REG}
podman push rocketmq:${ROCKETMQ_VERSION}-alpine ${REG}/${NAMESPACE}/rocketmq:${ROCKETMQ_VERSION}-alpine
```
