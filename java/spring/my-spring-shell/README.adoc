

# 目的
学习 [spring-shell](https://projects.spring.io/spring-shell/).
spring-shell 是从 spring-boot-cli 中抽离，并直接使用 spring IOC，而非 OSGI。


# 运行

```bash
./gradlew assemble
java -jar build/libs/my-spring-shell-0.1.0.jar
shell:>help         # 注意：可以使用 tab键来 word completion
shell:>help add

shell:>add 1 2 
3

shell:>echo 1 2 3               
You said a=1, b=2, c=3
shell:>echo --a 1 --b 2 --c 3   
You said a=1, b=2, c=3
shell:>echo 1 --c 3 2           
You said a=1, b=2, c=3

shell:>add2 --numbers 1 2 3.3
6.3

shell:>shutdown
You said false
shell:>shutdown --force
You said true

shell:>help         # 不可用的命令前面有个 * 号。
shell:>connect zhang3 123456
shell:>help
shell:>download
```


# FIMXE

- 若何不进入交互模式？如何与 bash 进行 pipe 操作？

    `echo help  | java -jar build/libs/my-spring-shell-0.1.0.jar`
    
    `java -jar build/libs/my-spring-shell-0.1.0.jar  <<< $(echo add 1 2)`

- [java.lang.IllegalStateException: Failed to execute ApplicationRunner](https://github.com/spring-projects/spring-shell/issues/167#issuecomment-335086379)