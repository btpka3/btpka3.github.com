
# 目的
该示例工程旨在演示在使用JPA的时候，如何使用Hibernate、JPA、和QueryDSL 的API操作数据库。对比并择取合适的。


# 运行

1. 启动数据库

    ```sh
mvn -q exec:java -Dexec.mainClass="org.h2.tools.Console" -Dexec.args="-tcpPort 9092 -webPort 8082"
    ```
1. 编译

    ```sh
mvn clean com.mysema.maven:maven-apt-plugin:process compile
    ```
1. 运行
    1. 运行使用 Hibernate API的例子

        ```sh
mvn -q exec:java -Dexec.mainClass="me.test.HibernateTest" -Dexec.args="add 11"
mvn -q exec:java -Dexec.mainClass="me.test.HibernateTest" -Dexec.args="update"
mvn -q exec:java -Dexec.mainClass="me.test.HibernateTest" -Dexec.args="list"
        ```
    1. 运行使用 JPA API的例子

        ```sh
mvn -q exec:java -Dexec.mainClass="me.test.JpaTest" -Dexec.args="add 11"
mvn -q exec:java -Dexec.mainClass="me.test.JpaTest" -Dexec.args="update"
mvn -q exec:java -Dexec.mainClass="me.test.JpaTest" -Dexec.args="list"
        ```

    1. 运行使用 QueryDSL API的例子

    ```sh
mvn -q exec:java -Dexec.mainClass="me.test.HibernateTest" -Dexec.args="add 11"
mvn -q exec:java -Dexec.mainClass="me.test.HibernateTest" -Dexec.args="update"
mvn -q exec:java -Dexec.mainClass="me.test.HibernateTest" -Dexec.args="list"
    ```


