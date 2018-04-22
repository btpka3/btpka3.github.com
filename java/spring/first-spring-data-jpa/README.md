


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

## 检查 JPA 定义与数据库表结构是否吻合

参考 `org.hibernate.tool.hbm2ddl.SchemaUpdate#main(String[])`

```bash


```

- Idea Lombok 插件启用：Preferences / Build / Compiler / Annotation Processors
  选中 `Enable annotation processing`