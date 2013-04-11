说明：
这个偶的第一个使用Maven和M2E创建的jar工程，会不断追加各种测试，目的就是为了熟练使用Maven。


##################################################### maven-assembly-plugin
# 命令：
  mvn clean package assembly:single
# 作用：将所有资源打包成 *.tar.gz, *.zip 等格式的发布包
# 不足：需要自己手写所有启动脚本


##################################################### maven-assembly-plugin
# 命令：
  mvn clean package appassembler:assemble appassembler:generate-daemons



TODO：
追加并配置Plugin
进行测试、
站点发布
...

FIXME：
Ctrl+Shfit+I可以检查自己工程中的代码的变量的值，然而对于引用的第三方的jar内的代码，即便
看到了source code，也无法 inspect（却能搞在 Variables 窗口中看到变量值）
Evaluations must contain either an expression or a block of well-formed statements

