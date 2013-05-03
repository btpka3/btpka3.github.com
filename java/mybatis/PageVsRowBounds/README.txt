目的：测试从大数据量中获取少量分页显示所需的数据对内存、性能的影响。

Step 1. 本地安装PostgreSql 和 MySql
Step 2. 在 PrepareDB 中运行分别运行
    mvn -P prepareMySql clean test
    mvn -P preparePostgreSql clean test

Step 3. 分别在以下PrepareDB、TestPage、TestRowBounds 下运行：
    mvn -P testMySql test
    mvn -P testPostgreSql test

PrepareDB       测试直接使用Jdbc API访问大数据量。但是目前仅仅是遍历所需的数据，而没有保存。
TestPage        测试通过MyBatis使用自定义Page对象，底层使用offset+limit进行分页。
TestRowBounds   测试通过MyBatis底层使用Jdbc API分页。 resultSet.ab

每个三次的记录数据如下。

【PrepareDB】 mvn -P testPostgreSql test      (275+260+242)/3=259     (13212272+13212272+13212272)/3=13212272
【PrepareDB】 mvn -P testMySql test           (122+122+120)/3=121     (15167680+15167680+15167680)/3=15167680
【TestPage】 mvn -P testPostgreSql test       (295+311+316)/3=307     (12972320+12972320+11827800)/3=12590813
【TestPage】 mvn -P testMySql test            (394+427+384)  =401     (12557248+10352528+12265984)/3=11725253
【TestRowBounds】 mvn -P testPostgreSql test  (584+556+626)/3=588     (23413760+23883840+23883168)/3=23726922
【TestRowBounds】 mvn -P testMySql test       (487+485+501)/3=491     (27316488+27351256+25949032)/3=26872258

PostgreSql
-----------------------------------------------------------
                    时间（毫秒）      内存（字节）
PrepareDB           259               13212272
TestPage            307 + 18.53%      12590813  -  4.70%
TestRowBounds       588 + 91.53%      23726922  + 88.45%


MySql
-----------------------------------------------------------
                    时间（毫秒）      内存（字节）
PrepareDB           121               15167680
TestPage            401 + 231.40%     11725253  -  22.70%
TestRowBounds       491 +  22.44%     26872258  + 129.18%



结论：

总体上，
（1）使用基于游标的会比使用limit+offset的多占内存，但仍然不会把满足记录的全部都加载到client端的内存，
所以不必担心内存溢出（因为要把99900条记录全部加在到内存中，远远不会仅增加1M多内存）。
注意：内存分析仅做参考，因为测试过程中常常发生垃圾回收而造成负值，而测试结果是仅仅挑选的正值。

（2）使用基于游标的会比使用limit+offset的多占时间。但是增加的时间量很小，且实际应用情况下，也不会一次性读取1000挑记录，通常是10~50条，
因此应用场景下，增加的几十微妙的时间可能会更短，可以忽略不计。


