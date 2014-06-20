

# 目的
该工程主要是用来学习 Hibernate-Tools 相关功能。


# 运行

## 启动数据库

```sh
mvn -q exec:java -Dexec.mainClass="org.h2.tools.Console" -Dexec.args="-tcpPort 9092 -webPort 8082"
```
之后，可以通过浏览器 http://127.0.1.1:8082/ 访问数据库。数据库连接信息如下

```txt
Driver Class    = org.h2.Driver
JDBC URL        = jdbc:h2:tcp://localhost:9092/~/first-hibernate-tools
User Name       = sa
Password        = <空>
```

## 反向生成Domain的java代码
1. 前提：需要数据库中表已经存在
    通过浏览器中访问数据库，并执行 `src/main/resources/me/test/domain/User4JAVA.sql`
1. 前提：需要手动编写 *.hbm.xml
    参见：`src/main/resources/me/test/domain/User4JAVA.hbm.xml`
1. 执行：生成java代码。

    ```sh
mvn -Dp_hbm2java clean antrun:run
    ```
    之后，会生成 `target/generated/java/me/test/domain/User4Java.java`。
    但请注意：由于User4JAVA.hbm.xml手动写的关联比较少，所有，该Java文件中也仅仅生成了一个ID和name。

## 从Domain的Java代码生成 *.hbm.xml

1. 前提：需要先存在Java文件，请参考 `src/main/java/me/test/domain/User4Hbm.java`
1. 执行：编译并生成 *.hbm.xml ：

    ```sh
mvn -Dp_hbm2hbmxml clean compile antrun:run
    ```
    之后，会生成 `target/generated/resources/me/test/domain/User4Hbm.hbm.xml`


## 从Domain的Java代码生成 ddl

1. 前提：需要先存在Java文件，请参考 `src/main/java/me/test/domain/User4Ddl.java`
1. 执行：编译并生成 ddl ：

    ```sh
mvn -Dp_hbm2ddl clean compile antrun:run
    ```
    之后，会在数据库中生成表，并将ddl保存在 `target/generated/resources/User4Ddl.sql`

