
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

gradle -DmainClass=me.test.first.spring.cloud.config.DemoConfigServer bootRun


curl localhost:8888/foo/development
```
 
 