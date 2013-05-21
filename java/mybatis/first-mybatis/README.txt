
1. 启动数据库
mvn -P startDB clean compile exec:java

2. 在浏览器中访问 http://localhost:8082 使用以下信息进行验证
Driver Class:   org.h2.Driver
JDBC URL:       jdbc:h2:tcp://localhost:9092/~/first-mybatis
User Name:      zhang3
Password:       zhang3

3. 生成Model，Mapper,Dao
mvn -P genSrc generate-sources 
