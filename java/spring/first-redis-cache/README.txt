1. start database
mvn -Dmaven.test.skip=true -Dp_startDb exec:java

2. generate query parameter classes
   (Optional) only needed when database schema is changed
mvn -Dp_genQueryObj querydsl:export
NOTICE: domain beans genereated by querydsl is not implements "java.io.Serializable" , added by hand temporarily

3. start jetty
mvn -Dmaven.test.skip=true -Dp_jetty jetty:run

4. do test
http://localhost:8080/

5. stop databse
in start database console, press enter key.
