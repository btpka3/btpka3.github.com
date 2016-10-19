

* [GORM for MongoDB](http://gorm.grails.org/latest/mongodb/manual/)
* [GORM for Hibernate](http://gorm.grails.org/latest/hibernate/manual/)
* [Spring Security Core Plugin](https://grails-plugins.github.io/grails-spring-security-core/v3/index.html)


```
# 以下命令会为关系数据库生成6个domain类:
# Role, RoleGroup, RoleGroupRole, User, UserGroup, UserRoleGroup
# 该示例工程中,上述几个类并没有使用到。
grails s2-quickstart my.grails3 User Role Requestmap --groupClassName=RoleGroup
grails s2-create-persistent-token my.grails3.PersistentLogin
grails s2-create-role-hierarchy-entry my.grails3.RoleHierarchyEntry

./gradlew :my-grails3:dependencies > my-grails3/.tmp/dep.txt
```

# Embedding mongodb
使用[de.flapdoodle.embed.mongo](https://github.com/flapdoodle-oss/de.flapdoodle.embed.mongo) 
的相关 [gralde 插件](https://github.com/sourcemuse/GradleMongoPlugin) 之后,
会将mongodb下载到 `~/.embedmongo/osx/mongodb-osx-x86_64-3.2.9.tgz`, 之后通过
以下命令启停mongodb

```
./gradlew :my-grails3:startManagedMongoDb
./gradlew :my-grails3:startMongoDb
./gradlew :my-grails3:stopMongoDb
```

# ElasticSearch

```
# docker cp my-es:/usr/share/elasticsearch/config /Users/zll/tmp/es-conf
docker stop my-es
docker rm my-es
docker run -itd \
        --name my-es \
        -p 9200:9200 \
        -p 9300:9300 \
        -v "/Users/zll/tmp/es-conf":/usr/share/elasticsearch/config \
        -v /Users/zll/tmp/es-data:/usr/share/elasticsearch/data \
        elasticsearch:2.4.1
docker start my-es

docker exec -it my-es bash

vi /Users/zll/tmp/es-conf/elasticsearch.yml 
cluster.name: "my-es"
node.name: "local"
index.number_of_shards: 1
index.number_of_replicas: 0

```

# Remember Me

相关类: PersistentTokenBasedRememberMeServices, GormPersistentTokenRepository, PersistentRememberMeToken,
 
其中token的默认有效期为 2周, 计时起始时间为上次自动登录的时间。