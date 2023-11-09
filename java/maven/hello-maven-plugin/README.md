

```sh
mvn -Dmaven.test.skip clean install
mvn -o com.github.btpka3:hello-maven-plugin:1.0.0-SNAPSHOT:sayhi
mvnDebug -o com.github.btpka3:hello-maven-plugin:1.0.0-SNAPSHOT:sayhi
# 需要 pom.xml
#mvn dependency:tree -Dincludes=org.springframework:spring-context:6.0.12
mvn com.github.ferstl:depgraph-maven-plugin:4.0.2:for-artifact \
  -DgroupId=org.springframework -DartifactId=spring-context -Dversion=6.0.12 \
  -DgraphFormat=text -DshowGroupIds=true -DshowVersions=true


# 生成bom 测试
BOM_TEMPLATE=`pwd`/src/test/resources/bom.tpl.xml
ls -l $BOM_TEMPLATE
cd /tmp
mvnDebug -Dbom.template=${BOM_TEMPLATE} com.github.btpka3:hello-maven-plugin:1.0.0-SNAPSHOT:gen-bom
```




# maven plugin 开发文档
- [PluginParameterExpressionEvaluator](https://maven.apache.org/ref/3.9.4/maven-core/apidocs/org/apache/maven/plugin/PluginParameterExpressionEvaluator.html)
  @Parameter 的字段可以使用哪些 placeholder, 以及替换后的 java 类型
- [Pretty-Print XML in Java](https://www.baeldung.com/java-pretty-print-xml)

# design

1. 准备数据：要排除的包的列表，以及替换成依赖的列表
1. 准备数据：准备要 dependencyManagement 的 依赖列表。 FIXME: 被 级联 import 的依赖中有需要拍报的依赖怎么处理？
1. 编程
   1. 获取完整依赖树，并从叶子节点开始处理
   2. 获取单个依赖的 依赖树，检查是否有需要排除/替换的包，并exclude; 检查要替换成依赖是否 被 dependencyManagement，没有则报错。
   3. 最后构建一个完整的bom，确保没有需要exclude的包。
   4. 重复class 的检查。
1. 检查要替换的依赖，在最终的的 war/fat jar 中是否有额外依赖，否则 warn/error.


# ref
- ferstl/depgraph-maven-plugin : https://github.com/ferstl/depgraph-maven-plugin/

# 备注

特殊的 aritifact 的 type
```xml
<dependency>
  <groupId>com.squareup.okio</groupId>
  <artifactId>okio-iosarm64</artifactId>
  <version>3.5.0</version>
  <type>klib</type>
</dependency>
```