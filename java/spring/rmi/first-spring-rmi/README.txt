


REFERENCE:
1. <<spring two-way rmi callback from server executing on client side>>
      http://stackoverflow.com/questions/3779134/spring-two-way-rmi-callback-from-server-executing-on-client-side
2. <<java.rmi.NoSuchObjectException: no such object in table>>
      http://stackoverflow.com/questions/645208/java-rmi-nosuchobjectexception-no-such-object-in-table



说明：
默认：使用Spring，默认，只有服务器端的实现代码是在服务端运行的，而 Callback（引用型对象） 则都是经过序列化之后在客户端JVM上运行的。
1. traditional : 
   缺点1：回调接口需要继承自 Remote。
   缺点2：回调接口实现类需要继承自 UnicastRemoteObject， 并提供抛出 RemoteException 的无参构造函数，
          且每个要暴露的方法都要声明抛出 RemoteException。
2. modify :
   可以避免缺陷2。但仍无法避免缺陷1。
   缺点1：回调接口需要继承自 Remote。
3. pojo : 修正上述两个缺点，但需要使用一个额外的工具类 ： RMIUtil 
