# 模块说明

- `demo-function` : 示例 function
- `demo-web` : 示例 PaaS， 尝试能运行时动态加载服务。


# 参考
- org.springframework.cloud.function.context.FunctionProperties
- org.springframework.cloud.function.deployer.FunctionDeployerConfiguration
- org.springframework.cloud.function.deployer.FunctionArchiveDeployer  
    参考： https://github.com/spring-cloud/spring-cloud-function/issues/564
    注意：这个是 package 级别 的，使用者是无法调用的。
  

Here is use case in my company:
System A is a risk management system, it's required to add some function at runtime without restarting.  
These function could be :
- simple data manipulate: such as `Map xxxFunction(Map params)`
- retrieve more detail info from other system: such as `Map getUserInfoById(Long userId)`
- send some data to other system to perform an action: such as `void deleteThreadById(Long threadId)`

System A is acted as FaaS. 
- When a new function which    
- When a new function which have very heavy local JVM manipulate (such as local cache, sync huge data from remote),
 the library is need to add to system A, then deploy , it takes very long time (at least one day). 




