该工程旨在学习使用Maven创建、测试、调试war工程。

http://docs.codehaus.org/display/JETTY/Debugging+with+the+Maven+Jetty+Plugin+inside+Eclipse
http://docs.codehaus.org/display/JETTY/Maven+Jetty+Plugin/
http://wiki.eclipse.org/Jetty/Feature/Jetty_Maven_Plugin#Quick_Start:_Get_Up_and_Running

调试步骤：

1. 创建远程调试用的外部启动jetty的命令
Eclipse :
  Run/External Tools/External Tools Configurations
  - 新建一个程序，first-war jetty:run
  - Location 为  ${env_var:M2_HOME}\bin\mvn.bat
  - Working Directory 为  ${workspace_loc:/first-war}
  - 参数为 jetty:run
  - 并新建环境变量 MAVEN_OPTS， 值为：-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=4000,server=y,suspend=n

2. 创建远程调试用的外部停止jetty的命令
仿照上面的例子创建一个 名称为 “first-war jetty:stop” 的外部程序，只有参数不一样，是  jetty:stop

3. 创建连接到远程调试服务器的debug命令：
Eclipse :
  Run/Debug Configurations...
  - 新建一个 Remote Java Application
  - 名称 first-war (remote)
  - project为要测试的工程，这里为 first-war
  - host 和 port 要与第一步中 MAVEN_OPTS 中的一致
  - 注意：记得把 Allow termination of remote VM 选中，这样就可以在调试完成后结束正在运行的jetty。
  - 如果不选中，也可以使用第二步创建的命令来停止jetty服务器。




FIXME
1. 使用远程debug模式，无法可以F7进入第三方jar包，能看到源代码，却不能查看变量
Evaluations must contain either an expression or block of well-formed statements

2. debug on server (比如 tomcat) 结果同上.

3. 如果直接使用 jetty:run 进行debug，则工程自己的源代码就无法关联上。
  - 手动关联之后，自己工程内的java代码可以查看变量的值。
  - 第三方依赖的jar包仍无法关联代码，就算一个一个手动关联之后，也无法查看变量的值
 Unable to evaluate the selected expression:
 To perform an evaluation, an expression must be compiled in the context of a java project's build path.
 The current execution context is not associated with a java project in the workspace.


可执行war？
http://open.bekk.no/embedded-jetty-7-webapp-executable-with-maven/

