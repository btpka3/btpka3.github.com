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

* RetryProperties
* ConfigClientHealthProperties
* ConfigServerHealthIndicator
* ConfigServerProperties
* ConsulEnvironmentWatch
* SvnKitEnvironmentRepository
* VaultEnvironmentRepository
* NativeEnvironmentRepository
* ConfigClientProperties
* MultipleJGitEnvironmentRepository


```text
application*  文件被多所有 config client 共享
```

# Controller

* EnvironmentController

* ResourceController
    通过 URL "/{spring.cloud.config.server.prefix}/{name}/{profile}/{label}/" 可以获取指定的资源。

```bash

# 查看给定的 name， profile 下，启用了哪些文件
curl http://localhost:8888/cfg/sc-config-client/default,test
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
curl http://localhost:8888/cfg/sc-config-client-default,test.yml
# 返回结果如下
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

配置文件搜索路径
1. ${GIT_REPO_ROOT}/
1. ${GIT_REPO_ROOT}/${searchPaths}


优先顺序
1. 有 profile 时，profile 最匹配的优先
1. profile 相同时，name 严格匹配的优先，application 名称的其次。


# 配置用git仓库规划
## 多个工程公用一个git仓库

```txt
${GIT_REPO_ROOT}/application.yml
${GIT_REPO_ROOT}/application-${profile}.yml
${GIT_REPO_ROOT}/${name}.yml
${GIT_REPO_ROOT}/${name}-${profile}.yml
```



