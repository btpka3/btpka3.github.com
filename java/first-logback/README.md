该工程主要用于在尝试使用logback的DBAppender前，先做个的demo，确认一下性能。

-------------------------------------------
结论1：如果没有启动worker线程的延迟设置的话，SLF4J会报以下错误，并造成只有一个logger把内容输出了：
SLF4J: The following set of substitute loggers may have been accessed
SLF4J: during the initialization phase. Logging calls during this
SLF4J: phase were not honored. However, subsequent logging calls to these
SLF4J: loggers will work as normally expected.
SLF4J: See also http://www.slf4j.org/codes.html#substituteLogger
SLF4J: me.test.worker_7
SLF4J: me.test.worker_1
SLF4J: me.test.worker_2
SLF4J: me.test.worker_9
SLF4J: me.test.worker_3
SLF4J: me.test.worker_4
SLF4J: me.test.worker_6
SLF4J: me.test.worker_8
SLF4J: me.test.worker_5

结论2：结论1中的问题，通过生命static logger可以有效缓解。

结论3：文件日志速度最快，DB日志慢了约50倍+，多运行几次，每个线程的运行时间甚至可以达到7秒。Aync+DB日志仅仅比DB日志略有性能提升，但可能会记录丢失。

FIXME：改进DBAppender为单线程、排它锁的方式插入DB应该能性能高点？


-------------------------------------------以下是测试结果
-----------------------------FILE,100%
me.test.worker_0 : 0:0:0.039
me.test.worker_1 : 0:0:0.037
me.test.worker_2 : 0:0:0.040
me.test.worker_3 : 0:0:0.034
me.test.worker_4 : 0:0:0.031
me.test.worker_5 : 0:0:0.029
me.test.worker_6 : 0:0:0.029
me.test.worker_7 : 0:0:0.032
me.test.worker_8 : 0:0:0.026
me.test.worker_9 : 0:0:0.025
main : 0:0:4.012


-----------------------------DB,100%
me.test.worker_0 : 0:0:2.222
me.test.worker_1 : 0:0:2.222
me.test.worker_2 : 0:0:2.350
me.test.worker_3 : 0:0:2.398
me.test.worker_4 : 0:0:2.522
me.test.worker_5 : 0:0:2.520
me.test.worker_6 : 0:0:2.309
me.test.worker_7 : 0:0:2.105
me.test.worker_8 : 0:0:1.841
me.test.worker_9 : 0:0:1.609
main : 0:0:5.218


-----------------------------asyncDB,100%
me.test.worker_1 : 0:0:1.958
me.test.worker_0 : 0:0:1.966
me.test.worker_2 : 0:0:2.033
me.test.worker_3 : 0:0:2.013
me.test.worker_4 : 0:0:2.039
me.test.worker_5 : 0:0:1.922
me.test.worker_6 : 0:0:1.781
me.test.worker_7 : 0:0:1.632
me.test.worker_8 : 0:0:1.428
me.test.worker_9 : 0:0:1.317
main : 0:0:4.921

