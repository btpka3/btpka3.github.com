sample/tomcat6
  is copyed from CentOS 6.2 after using yum installing tomcat6

app.init.d
  - 以指定的用户启动、停止给定的命令。并创建锁文件、PID文件、给定命令输入日志文件。

app
  - 只是简单的启动，停止相应的程序，并且将pid保存到由 app.init.d 指定的PID文件中。

  
  
===============================================================================

在同一行不断更新时间。
while [ 1 ]; do printf "\r$(date +%T)"; sleep 1s; done
