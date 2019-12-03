


# 目的

- JdbcTemplate
- JPA
- Jooq 
	- JdbcTemplate
    - jpa
- queryDsl
- mysql [sakila](https://dev.mysql.com/doc/sakila/en/) example database
 


## 参考
- [Sakila Sample Database](https://dev.mysql.com/doc/sakila/en/sakila-introduction.html)
- MySql Workbench 自带了 `sakila_full.mwb` ER图 模型文件
- [jpa-sakila](https://github.com/saltnlight5/java-ee6-examples/tree/master/extra/jpa-sakila)
- JPA
    - [OneToOne](https://en.wikibooks.org/wiki/Java_Persistence/OneToOne)
    - [OneToMany](https://en.wikibooks.org/wiki/Java_Persistence/OneToMany)
    - [MayToOne](https://en.wikibooks.org/wiki/Java_Persistence/ManyToOne)
    - [ManyToMany](https://en.wikibooks.org/wiki/Java_Persistence/ManyToMany)
    - [ElementCollection](https://en.wikibooks.org/wiki/Java_Persistence/ElementCollection)



## 说明

- my-domain-jooq : 从domain类生成 相关类



## 检查 JPA 定义与数据库表结构是否吻合

参考 `org.hibernate.tool.hbm2ddl.SchemaUpdate#main(String[])`

```bash
# 通过docker 本地启动 mysql 数据库，如何创建，咱时省略。
docker start my-mysql

```




- Idea Lombok 插件启用：Preferences / Build / Compiler / Annotation Processors
  选中 `Enable annotation processing`
  
- 采取 [70% JPA + 30% JOOQ](https://www.jooq.org/doc/3.11/manual/getting-started/use-cases/) 的方案
- 手动创建 domain 类，要求与 数据库表结构相匹配
- 通过 [code generation](https://www.jooq.org/doc/3.11/manual/code-generation/) 来生成相关辅助类
    - 配置 maven 插件 jooq-codegen-maven, 或配置 gradle 插件 [nu.studer.jooq](https://github.com/etiennestuder/gradle-jooq-plugin)
    - [Code generation : JPADatabase: Code generation from entities](https://www.jooq.org/doc/3.11/manual/code-generation/codegen-jpa/)



## 问题小结
- [jooq 扫描不到 jpa 实体](https://github.com/etiennestuder/gradle-jooq-plugin/issues/84)
