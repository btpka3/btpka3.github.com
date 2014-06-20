
# 目的
该示例工程旨在演示如何使用Hibernate、JPA、和QueryDSL


mvn -q exec:java -Dexec.mainClass="org.h2.tools.Console" -Dexec.args="-tcpPort 9092 -webPort 8082"

mvn clean com.mysema.maven:maven-apt-plugin:process compile

mvn -q exec:java -Dexec.mainClass="me.test.HibernateTest" -Dexec.args="add 11"
mvn -q exec:java -Dexec.mainClass="me.test.HibernateTest" -Dexec.args="update"
mvn -q exec:java -Dexec.mainClass="me.test.HibernateTest" -Dexec.args="list"

mvn -q exec:java -Dexec.mainClass="me.test.JpaTest" -Dexec.args="add 11"
mvn -q exec:java -Dexec.mainClass="me.test.JpaTest" -Dexec.args="update"
mvn -q exec:java -Dexec.mainClass="me.test.JpaTest" -Dexec.args="list"

mvn -q exec:java -Dexec.mainClass="me.test.HibernateTest" -Dexec.args="add 11"
mvn -q exec:java -Dexec.mainClass="me.test.HibernateTest" -Dexec.args="update"
mvn -q exec:java -Dexec.mainClass="me.test.HibernateTest" -Dexec.args="list"



