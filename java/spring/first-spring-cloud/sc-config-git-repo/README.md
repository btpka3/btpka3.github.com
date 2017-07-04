模拟 git 仓库，配置文件的内容来源。


```bash
# 为了代码方便演示，给出一个符号链接
ln -s $(realpath ../../../) ~/btpka3.github.com
```

* Environment 资源。通过三个参数去定位：
    * {application} : 匹配 client 端 bootstrap.yml 中 `spring.application.name` 的值
    * {profile} : 匹配 client 端 bootstrap.yml 中 `spring.profiles.active` 的值
    * {label} : 是服务器端的一个特性，可以理解为 version

EnvironmentRepository
    EnvironmentEncryptorEnvironmentRepository
    CompositeEnvironmentRepository
    AbstractScmEnvironmentRepository
        JGitEnvironmentRepository
            MultipleJGitEnvironmentRepository
    NativeEnvironmentRepository
    
# 参考: 

* https://github.com/spring-cloud-samples/configserver
* https://github.com/spring-cloud-samples/config-repo
* https://stackshare.io/stackups/consul-vs-zookeeper-vs-eureka

# ConfigurationProperties

```txt
* RetryProperties                   : spring.cloud.config.retry
* ConfigClientHealthProperties      : health.config
* ConfigServerHealthIndicator       : spring.cloud.config.server.health
* EnvironmentRepositoryConfiguration: spring.cloud.config.server.health.enabled
* ConfigServerProperties            : spring.cloud.config.server
* ConsulEnvironmentWatch            : spring.cloud.config.server.consul.watch
* SvnKitEnvironmentRepository       : spring.cloud.config.server.svn
* VaultEnvironmentRepository        : spring.cloud.config.server.vault
* NativeEnvironmentRepository       : spring.cloud.config.server.native
* ConfigClientProperties            : spring.cloud.config
* MultipleJGitEnvironmentRepository : spring.cloud.config.server.git

EncryptorFactory
EncryptionAutoConfiguration
EncryptionBootstrapConfiguration
* KeyProperties                     : encrypt

EnvironmentMonitorAutoConfiguration

```

# Controller
## EncryptionController

```bash
# 加密
curl http://localhost:10010/cfg/encrypt -d 123456
13763946e638caeacec62529d13ea4a9c8dad0f996795e5e88448ff9a124250c
curl http://localhost:10010/cfg/encrypt -d 123456
e1599a7106caf7dc6abcf35a8158db934b0bb7156dd73a11a1a9d1dd642f9a06

# 解密
curl http://localhost:10010/cfg/decrypt -d e1599a7106caf7dc6abcf35a8158db934b0bb7156dd73a11a1a9d1dd642f9a06
123456
```


## EnvironmentController
```bash

# 查看给定的 name， profile 下，启用了哪些文件
curl http://localhost:10010/cfg/sc-config-client/default,test
# 返回JSON 内容指明启用了以下文件
/aaa/sc-config-client/sc-config-client-test.yml
/aaa/sc-config-client/application-test.yml
/sc-config-client-test.yml
/application-test.yml
/aaa/sc-config-client/sc-config-client.yml
/aaa/sc-config-client/application.yml
/sc-config-client.yml
/application.yml

# 查看合并后的内容
curl http://localhost:10010/cfg/sc-config-client-default,test.yml
# 返回结果如下
password: '123456'
path:
  /aaa/sc-config-client/application: true
  /aaa/sc-config-client/application-test: true
  /aaa/sc-config-client/sc-config-client: true
  /aaa/sc-config-client/sc-config-client-test: true
  /application: true
  /application-test: true
  /sc-config-client: true
  /sc-config-client-test: true
```

## ResourceController
    通过 URL "/{spring.cloud.config.server.prefix}/{name}/{profile}/{label}/" 可以获取指定的资源。

```text
curl -v http://localhost:10010/cfg/sc-config-client/default/master/a.json    # Content-Type: text/plain;charset=UTF-8
{
  "youPwd": "123456"
}

curl -v http://localhost:10010/cfg/sc-config-client/default/master/a.xml     # Content-Type: text/plain;charset=UTF-8
<?xml version="1.0" encoding="UTF-8"?>
<root>
    <yourPwd>123456</yourPwd>
* Curl_http_done: called premature == 0
* Connection #0 to host localhost left intact
</root>
```

配置文件搜索路径
1. ${GIT_REPO_ROOT}/
1. ${GIT_REPO_ROOT}/${searchPaths}


优先顺序
1. 有 profile 时，profile 最匹配的优先
1. profile 相同时，name 严格匹配的优先，application 名称的其次。



## PropertyPathEndpoint

# 配置用git仓库规划
## 多个工程公用一个git仓库

```txt
${GIT_REPO_ROOT}/application.yml
${GIT_REPO_ROOT}/application-${profile}.yml
${GIT_REPO_ROOT}/${name}.yml
${GIT_REPO_ROOT}/${name}-${profile}.yml
```



