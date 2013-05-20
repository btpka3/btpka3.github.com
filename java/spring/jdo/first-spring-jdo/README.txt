querydsl-maven-plugin 需要依赖Domain Object的class才能生成相应的Query Object的Java类。
所以该示例工程应当分成以下几个包：
first-spring-jdo-api : 只保存 Domain Object 的Java文件
first-spring-jdo-dsl-jdo : 依赖上个工程的编译结果，然后生成相应的QBean，
first-spring-jdo-app : 依赖上面两个工程

但是，该示例工程在 M2Eclipse 环境下，通过 Project-> Maven -> Update Project 可以运行。
只是不能通过Run AS -> Maven Build 运行。