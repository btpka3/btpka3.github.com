

# start spark standalone cluster


```
# 启动master
# 可以访问 http://localhost:8080/
./sbin/start-master.sh

# 单独启动一个Worker
# 可以访问 http://localhost:8081/
# 可以访问 http://localhost:8082/
./bin/spark-class org.apache.spark.deploy.worker.Worker  spark://z:7077 -c 1 -m 512M
./bin/spark-class org.apache.spark.deploy.worker.Worker  spark://z:7077 -c 1 -m 512M
```



























# scala
[specification](http://www.scala-lang.org/files/archive/spec/2.11/)
## 词法

* 变量名都小写开头
* id 可以是 &#96;xxx&#96; 格式构成的字符串
* 以下为保留词

```
abstract    case        catch       class       def
do          else        extends     false       final
finally     for         forSome     if          implicit
import      lazy        macro       match       new
null        object      override    package     private
protected   return      sealed      super       this
throw       trait       try         true        type
val         var         while       with        yield
_    :    =    =>    <-    <:    <%     >:    #    @
⇒    ←
```

* 以分号或者换行结束语句。注意：换行符可能用来结束前一条语句，也有可能开始下一条语句。
但在圆扩号之间、方括号之间，XML模式代码之间，换行符的这种作用是没有被启用的


   ```
   new Iterator[Int]
   {
     private var x = 0
     def hasNext = true
     def next = { x += 1; x }
   }
   // 上面的代码如果像下面这样增加一个i额外的换行符，
   // 含义就变成了先new个对象，后面是一个本地代码块
   new Iterator[Int]

   {
     private var x = 0
     def hasNext = true
     def next = { x += 1; x }
   }
   ```
* 允许使用 """long text""" 来定义长的、包含换行符的字符串

## 标志符、名字、作用域