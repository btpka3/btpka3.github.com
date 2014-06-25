# 目的
    学习如何在Grails中使用RabbmitMQ进行开发。并尽量按照正式开发流程进行演示。

# 工程创建步骤
1. 使用命令 `grails create-app my-rabbitmq` 创建工程。再使用IDEA导入工程。
1. 修改 grails-app/conf/BuildConfig.groovy: 设置使用Maven仓库，追加对spring-rabbit和jackson-mapper-asl的依赖
1. 创建 spring配置文件 : grails-app/conf/spring/resources.xml。使用 rabbitmq 命名空间来简化 queue，exchange，RabbimtTemplate、SimpleMessageListenerContainer的配置、声明。
1. 创建 service : grails-app/services/me/test/TestMqService.groovy 给出服务端的实现
1. 创建 controller : grails/my-rabbitmq/grails-app/controllers/me/test/TestController.groovy 给出Client端的实现

# 演示步骤：
需先本地启动RabbitMQ服务器，使用命令 `grails run-app` 启动该工程，并访问、刷新 http://localhost:8080/my-rabbitmq/test 。
