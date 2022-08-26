


```bash
docker create                                       \
    --name my-mysql                                 \
    --restart unless-stopped                        \
    -it                                             \
    -e MYSQL_ROOT_PASSWORD=123456                   \
    -v /Users/zll/data0/store/soft/mysql:/var/lib/mysql                  \
    -p 3306:3306                                    \
    mysql:5.7.21

docker start my-mysql


# 通过docker 本地启动 mysql 数据库，如何创建，咱时省略。
docker start my-mysql

```
- [The jOOQ Release Note History](https://www.jooq.org/notes)
  - 3.17.0 : OSS 版本需要 JDK 17
  - 3.15.0 : OSS 版本需要 JDK 11
- [Version Support Matrix](https://www.jooq.org/download/support-matrix)
- 3.16 [文档](https://www.jooq.org/doc/3.16/manual-single-page/)：
  - 自定义数据类型的绑定: [4.22.5. Custom data type bindings](https://www.jooq.org/doc/3.16/manual-single-page/#custom-bindings)
  - [6.2.5.15.3. Qualified converters](https://www.jooq.org/doc/3.16/manual-single-page/#codegen-database-forced-types-converter)
- gradle plugin : [nu.studer.jooq](https://github.com/etiennestuder/gradle-jooq-plugin)
  - org.jooq:jooq-codegen
    - org.jooq.meta.jaxb.Configuration
- 示例数据库： [sakiladb/mysql](https://github.com/sakiladb/mysql)
- docker 镜像: [sakiladb/mysql](https://hub.docker.com/r/sakiladb/mysql)
- org.jooq.meta.jaxb.Configuration
- 
# 启动数据库
```bash

cd src/test/resoures
docker-compose up
```

# 生成代码
```bash
./gradlew clean build
```
