
# 背景

在使用 IDEA 编写 Java 代码的时候，常常看到黄色的警告——应当提成字符串常量。

一直在自己猜测 Why，也能想出原因： 
每个class文件都有个常量池，每个类都有一个同样的字符串常量，那不就是jar包尺寸变大了？
直到最近才通过一个[shell脚本](https://github.com/btpka3/btpka3.github.com/blob/master/java/jdk/TestJDK/hello/src/test/testStrConstants.sh) 验证了自己的想法。

再写那个shell脚本的时候，常量引用，让想起了08年刚毕业时第一份工作(北京恩梯梯——对日外包)时遇到的一个坑：
一个jar包引用了日方给的 API 包，但 API 包版本升级后，我们编写的依赖的Jar 包没重新编译，早成出错。
原因就是 int 型常量在编译时是直接复制值的，而不会到运行时调用 API jar包中 值已经变更的 int 型常量。

再深入一点，即便多个类有相同的字符串常量，加载 JVM 全部加载到内存中的时候，应该只有占用一份字符串常量内存吧？

捣鼓到字节码的问题，突然想尝试一下关于Java的逆向工程了——虽然逆向工程往往与破解、盗版沾边。
于是就拿日常使用的 UML 工具 —— Astah Community 的 专业版动试手吧。
Astah Professional 7.x 版本在搜索引擎上搜不到什么破解信息，这样的才有动力嘛。
PS：让想起了大学期间，学习ASM/C/C++的时候，到看雪论坛上下载各种工具，反编译 exe 程序，
修改关键 jump 时的那些怀念的时光了。

# 流程记录

- 找到 并解压 astah-pro.jar， 不惊讶，都经过混淆了，有N多root package中类。
- 尼玛，突然发现，自己写 Java 代码，是无法调用 root package 中的类的。
  javac 编译时做了该限制，但人家混淆后的类为何能调用？
  答案应该有了，字节码级别的仍然保留着呢，那就只能通过字节码修改来处理了。
  
- 字节码工具很多的，哪款呢？ASM——都说很底层，很繁琐，那 其他的 cglib， javassist 呢？
  都先写个简单 demo 吧。
- 但最终发现 还是 asm 的灵活些，提供了工具能直接将class 文件输出为调用 asm文件的源代码。
  然后再在该源代码的基础上修改，就 相对easy 了（这也仅仅是相对，要想熟练，还是熟悉各种 JVM 指令才行）

- 工具准备完毕，开始找突破点吧。根据 Astah Professional 关于评估版提示的信息，
  可以在 properties 文件中找到对应的 message 的 key——  "license_evaluation_term.message"。

- 再根据该 message key，在各个class中找。首要目标是让 Astah Professional 长期可用，而非要破解注册码生成机制。
  最简单的就是对 评估板 时长进行修改。这个具体就看 src/main/java/io/github/btpka3/asm/H.java 吧。

# 参考
- net.sf.cglib.samples.Bean


# FIXME

- 给定的一个类，引用了哪些外部类？
- 给定一个类的某个方法，该方法被哪些地方调用过？
- 替换一个类的某个方法的代码逻辑？
