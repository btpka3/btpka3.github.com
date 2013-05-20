参考：
http://www.datanucleus.org/products/accessplatform_3_2/jdo/api.html
http://db.apache.org/jdo/api30/apidocs/index.html

DAO定义：
这里的dao设计没有提供复杂查询功能。
更新时，因为数据库字段有的是允许为null的，所以无法根据值是否为null来确认是否要更新该字段，因此改为使用key的存在性判断是否要更新该字段。
没有为关系类定义dao。

FIXME：是否还有必要定义dao了？


DB设计：
  User - 用户表
    id        - 单主键，AUTO INCREASE
    name
    age
    male
    imgData
    birthday
    version   - 版本更新字段，可以用于乐观锁

  UserGroup - 用户组表
    id        - 单主键，从Sequence中读取
    description
    version   - 版本更新字段，可以用于乐观锁

  UserGroupMember - 用户组成员表
    uid       - 用户ID，组合主键1
    gid       - 用户组ID，组合主键2

mapping
  class mapping
      个人觉得应该使用使用XML metadata，而不要使用 JDO Annotation。参考：
      http://db.apache.org/jdo/metadata.html
      http://www.datanucleus.org/products/accessplatform_3_2/jdo/metadata_xml.html

  identity
      有以下四种类型：
      Datastore Identity (推荐)
          这个是默认的，特点是其值完全由DataNucleus负责生成与获取，
          对于JDO使用者而言无需、也不应该关心它的值与类型
          domain class无需为其声明相应的字段

          因此，只能：persistenceManager.getObjectId(Object)，
          而不能自己new一个IDd对象，再调用 persistenceManager.getObjectById(Object)
          但是，我们仍然可以通过Query对象进行查询。

      Application Identity
          对于组合主键的table，需要额外定一个主键类
          http://www.datanucleus.org/products/accessplatform_3_2/jdo/primary_key.html

          对于单主键，可以不用创建主键类，无需在*.jdo中指定objectid-class，只需在相应field上指定 primary-key="true"，调用时示例：
            LongIdentity id = new LongIdentity(myDomainClass, 101);
            persistenceManager.getObjectById(id);
          或者
            persistenceManager.getObjectById(myDomainClass, Long.valueof(101L));

          对于组合主键，如果没有指定相应的主键类，DataNucleus enhancer会自动生成一个。
          http://www.datanucleus.org/products/accessplatform_3_2/jdo/enhancer.html
          要开发者自定义主键类，还是比较麻烦的，比如：主键类必须有个接受String参数的构造函数，必须覆盖toString()方法，且toString()的返回值能用于构造函数。

      Nondurable Identity
          特点：数据库表中常常没有主键，而是靠DataNucleus在Client端为其创建一个，以方便使用。

      Compound Identity
          ???


querydsl-maven-plugin
http://www.querydsl.com/static/querydsl/3.1.1/reference/html/ch03s03.html


<!--
  http://www.datanucleus.org/products/datanucleus/jdo/metadata.html
  http://www.datanucleus.org/products/datanucleus/jdo/metadata_xml.html
  http://db.apache.org/jdo/metadata.html
-->