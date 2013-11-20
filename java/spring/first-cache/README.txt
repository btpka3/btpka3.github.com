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

*  同个cache中，值的类型必须一致。
   不能有的是List，有的是单个对象，否则等到反序列化是，可能造成Java类型不匹配而出错。

*  同个cache中，key的生成方式必须一致，且只能以方法参数作为生成key的数据来源，而不能是返回值。
   不要使用Spring默认的Key的生成机制，该机制只能保证调用相同的参数列表的方法时，相同参数才能生成相同的key。
   而由于参数列表不一致、结果却相同的方法将会使用不同的cache缓存副本。会造成更新缓存时只能全部清除，否则会漏删而造成数据不同步。
   因此必须先约定好指定cache的key的生成规则，然后统一在Spring cache注解中通过SpEL指定key。

* c)  建议，同类型的单个对象应被存放在同一个cache中，以ID作为key（ID始终不变）。集合类对象应当按类型另外存储不同的cache。
单个对象更新时，应当按照ID从单对象cache中删除，并将集合类对象cahce清空。

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
3.  Ehcache使用小结
a)  集群时，
 Cache中新插入条目时，可以不用通知其他节点。
 Cache中覆盖某个条目时，可以通知其他节点进行单个删除操作，而不用将新值传输过去（因为KEY的字节数小，值的字节数大）。
 Cache中删除某个条目时，必须通知其他节点进行单个删除操作。

b)  Ehcache是在JVM内部进行内存缓存的，配合异步通知其他节点模式。个人感觉（虽未验证）：相当于直接从内存中读取，
    而没有网络开销（异步事件周知可以当做不阻塞当前线程而无性能消耗）会比Redis等集中存储方式减少网络消耗，会更快些。
c)  如果集群规模很大，则不应该使用RMI方式，而应该使用JMS方式集群。否则可能会产生过多的广播消息。


~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

@Cacheable(value="cacheName", key="SpEL")
执行被标注的方法前，先计算key，并到指定的cache检查是否已经缓存有结果。
如果有，则直接返回结果，而不再执行方法体。
如果没有，则执行方法体，并将返回值存放到cache中

@CachePut(value="cacheName", key="SpEL")
  始终都执行方法体，并将返回值放到cache中

@CacheEvict(value="cacheName", key="SpEL")
  执行方法前/后，从指定的cache中清除指定的key或所有
