1. start database
`mvn -Dmaven.test.skip=true -Dp_startDb exec:java`

2. generate query parameter classes
   (Optional) only needed when database schema is changed
`mvn -Dmaven.test.skip=true -Dp_genQueryObj process-classes`

3. start jetty node1
`mvn -Dmaven.test.skip=true -Dp_node1 jetty:run-forked`

4. start jetty node2
`mvn -Dmaven.test.skip=true -Dp_node2 jetty:run-forked`

5. test clustered ehcache
  5.1 visite http://localhost:8081/node1/ws/user/1 more than twice, 
      console1 only dispalyed one: "$$$$$$$$$$$$$$$$$$$$$$$ selectById()", 
      console2 output nothing.
      
  5.2 visite http://localhost:8082/node2/ws/user/1 more than twice,    
      console1 output nothing. 
      console2 output : "$$$$$$$$$$$$$$$$$$$$$$$ selectById()",

  5.3 modify user1's signature and click update button on node1.
      console1 only dispalyed one: "$$$$$$$$$$$$$$$$$$$$$$$ update(User)" and "$$$$$$$$$$$$$$$$$$$$$$$ selectById()", 
      console2 output nothing.

  5.4 visite http://localhost:8081/node1/ws/user/1 more than twice, 
      console1 output nothing. 
      console2 output nothing.
      
  5.5 visite http://localhost:8082/node2/ws/user/1 more than twice,    
      console1 output nothing. 
      console2 output : "$$$$$$$$$$$$$$$$$$$$$$$ selectById()",

6. stop jetty node1
`mvn -Dmaven.test.skip=true -Dp_node1 jetty:stop`

7. stop jetty node2
`mvn -Dmaven.test.skip=true -Dp_node2 jetty:stop`

8. stop databse
in start database console, press enter key.

-------------------------------------------------

* 同一个cache中的值的类型必须一致。
    比如：不能单个的对象和list混合存储
* 同个cache中key的生成方式必须一致，且只能以参数中的值作为key的来源（而不能以返回值）。
    比如：如果value是单个对象，则key可能需要统一为使用ID作为key。
    否则：可能会不同的key都缓存同个对象的多份拷贝；查询时用key1缓存，更新时却删除key2;
* 如果单个DB对象被更新，而该对象在单个cache中存在多个副本时，只能全部清空缓存。


@Cacheable(“cacheName”)
指定方法体前，先按照参数计算key，并到指定的cache检查是否已经缓存有结果。
如果有，则直接返回结果，而不再执行方法体。
如果没有，则执行方法体，并将返回值存放到cache中

@CachePut(“cacheName”)
  始终都执行方法体，并将返回值放到cache中

@CacheEvict
  执行方法前/后，从指定的cache中清除指定的key或所有
