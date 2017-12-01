模拟 git 仓库，配置文件的内容来源。\

参见 顶层 git 仓库 [sc-config-git-repo](https://github.com/btpka3/sc-config-git-repo)


- 启动 sc-config-server

    ```bash
    # 确保已经安装了 JCE
    
    # 为了代码方便演示，给出一个符号链接
    ln -s $(realpath ../../../) ~/btpka3.github.com
    
    ./gradlew :sc-config-server:bootRun
    ```

- 验证 sc-config-server ： EncryptionController

    ```bash
    # EncryptionController: 加密
    curl http://localhost:10010/cfg/encrypt -d 123456
    13763946e638caeacec62529d13ea4a9c8dad0f996795e5e88448ff9a124250c
    curl http://localhost:10010/cfg/encrypt -d 123456
    e1599a7106caf7dc6abcf35a8158db934b0bb7156dd73a11a1a9d1dd642f9a06
    
    # EncryptionController: 解密
    curl http://localhost:10010/cfg/decrypt -d e1599a7106caf7dc6abcf35a8158db934b0bb7156dd73a11a1a9d1dd642f9a06
    123456
    ```
    

- 验证 sc-config-server ： EnvironmentController

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

- 验证 sc-config-server ： ResourceController

    通过 URL "/{spring.cloud.config.server.prefix}/{name}/{profile}/{label}/" 可以获取指定的资源。
    
    ```bash
    # Content-Type: text/plain;charset=UTF-8
    curl -v http://localhost:10010/cfg/sc-config-client/default/master/a.json    
    {
      "youPwd": "123456"
    }
    
    # Content-Type: text/plain;charset=UTF-8
    curl -v http://localhost:10010/cfg/sc-config-client/default/master/a.xml     
    <?xml version="1.0" encoding="UTF-8"?>
    <root>
        <yourPwd>123456</yourPwd>
    </root>
    ```

- 验证 sc-config-server : PropertyPathEndpoint 
    
    默认是 `/monitor`, 在收到 Git 仓库对该 URL 的 webhook 之后，会发送 RefreshRemoteApplicationEvent
    需要在 config server 端和 client 端都配置 spring-cloud-bus。

- 验证 sc-config-client

    ```bash
    ./gradlew :sc-config-client:bootRun
  
    curl -v http://localhost:10020/test/hi
    {"env":{
      "password":"123456",
      "path._aaa_sc-config-client_application":"true",
      "foo":"bar",
      "server.port":"10020",
      "path._application":"true"},
    "a":"aaa",
    "date":1512140322801}
    ```


# FIXME

- 响应时间太慢，请求一个静态文本资源 `/a.json` 需要17秒。
- git 仓库更新后通知。


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




* https://start.spring.io/
* http://microservices.io/

* [Config Server](https://github.com/spring-cloud-samples/configserver)
* [Config Clients](https://github.com/spring-cloud-samples/customers-stores)
https://github.com/spring-cloud/spring-cloud-config/blob/master/spring-cloud-config-sample/pom.xml

```

# 准备配置仓库

cd $HOME
mkdir config-repo
cd config-repo
git init .
echo info.foo: bar > application.properties
git add -A .
git commit -m "Add application.properties"

#gradle -DmainClass=me.test.first.spring.cloud.config.ScConfigServerApp bootRun
./gradlew bootRun

curl localhost:10010/foo/development
```
 