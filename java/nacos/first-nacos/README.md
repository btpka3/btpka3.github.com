


## 环境准备


下载 nacos

```bash
mkdir -p ~/data0/soft/nacos

# 参考： https://github.com/alibaba/nacos/releases/tag/2.2.3
wget -O ~/data0/soft/nacos/nacos-server-2.2.3.zip \
     https://github.com/alibaba/nacos/releases/download/2.2.3/nacos-server-2.2.3.zip
     
unzip nacos-server-2.2.3.zip
```

启动 nacos

```shell
sdk use java 8.0.332-zulu
export NACOS_HOME=~/data0/soft/nacos/nacos
${NACOS_HOME}/bin/startup.sh -m standalone
```
浏览器访问: [http://127.0.0.1:8848/nacos/](http://127.0.0.1:8848/nacos/)


发布配置
```shell
NACOS_ADDR=http://127.0.0.1:8848
#NACOS_ADDR=http://nacos.default.svc.cluster.local:8848

namespaceId=public
dataId=gong9.mw.tddl.conf
group=gong9-mw
content='{"a":"aaa"}'

curl -v -X POST "${NACOS_ADDR}/nacos/v2/cs/config" \
  -d "dataId=${dataId}" \
  -d "group=${group}" \
  -d "namespaceId=${namespaceId}" \
  -d "content=${content}"

```


获取配置
```shell
NACOS_ADDR=http://127.0.0.1:8848
#NACOS_ADDR=http://nacos.default.svc.cluster.local:8848

namespaceId=public
dataId=gong9.mw.tddl.conf
group=gong9-mw

curl -v -X GET "${NACOS_ADDR}/nacos/v2/cs/config?dataId=${dataId}&group=${group}&namespaceId=${namespaceId}"
```



 