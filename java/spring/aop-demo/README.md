Spring AOP 示例
1. me.test.xml.Main 示例使用Spring声明式AOP
2. TODO 使用@Aspect等注解运用AOP。

* output
* me.test.xml.xmlMain
```
-------------
taskA is running.
-------------
111111111 - xml :{a=a1, b=b1}
taskB is running.
222222222 - xml :{a=a1, b=b1}
-------------
```

* me.test.anno.AnnoMain
```
spring managed 12 beans:
  bean name  : configMap
  getClass() : class java.util.LinkedHashMap
  toString() : {a=a1, b=b1}

  bean name  : myPojoAspectLogic
  getClass() : class me.test.xml.MyPojoAspectLogic
  toString() : me.test.xml.MyPojoAspectLogic@2c4689ad

  bean name  : taskA
  getClass() : class me.test.task.TaskA
  toString() : me.test.task.TaskA@4af8d390

  bean name  : taskB
  getClass() : class com.sun.proxy.$Proxy8
  toString() : me.test.task.TaskB@16c8b5a6

  bean name  : myAnnoAspect
  getClass() : class me.test.anno.MyAnnoAspect
  toString() : me.test.anno.MyAnnoAspect@530db0f9

  bean name  : org.springframework.context.annotation.internalConfigurationAnnotationProcessor
  getClass() : class org.springframework.context.annotation.ConfigurationClassPostProcessor
  toString() : org.springframework.context.annotation.ConfigurationClassPostProcessor@27ddd608

  bean name  : org.springframework.context.annotation.internalAutowiredAnnotationProcessor
  getClass() : class org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor
  toString() : org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor@550ebbaa

  bean name  : org.springframework.context.annotation.internalRequiredAnnotationProcessor
  getClass() : class org.springframework.beans.factory.annotation.RequiredAnnotationBeanPostProcessor
  toString() : org.springframework.beans.factory.annotation.RequiredAnnotationBeanPostProcessor@3a42c186

  bean name  : org.springframework.context.annotation.internalCommonAnnotationProcessor
  getClass() : class org.springframework.context.annotation.CommonAnnotationBeanPostProcessor
  toString() : org.springframework.context.annotation.CommonAnnotationBeanPostProcessor@74af624b

  bean name  : org.springframework.aop.config.internalAutoProxyCreator
  getClass() : class org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator
  toString() : proxyTargetClass=false; optimize=false; opaque=false; exposeProxy=false; frozen=false

  bean name  : org.springframework.aop.aspectj.AspectJPointcutAdvisor#0
  getClass() : class org.springframework.aop.aspectj.AspectJPointcutAdvisor
  toString() : org.springframework.aop.aspectj.AspectJPointcutAdvisor@517a6a7f
  pointcut   : ComposablePointcut: ClassFilter [AspectJExpressionPointcut: () me.test.anno.MyAnnoAspect.myAnnoPointcut()], MethodMatcher [org.springframework.aop.support.MethodMatchers$IntersectionMethodMatcher@4ef79ee8]
  advice     : org.springframework.aop.aspectj.AspectJAfterAdvice: advice method [public void me.test.xml.MyPojoAspectLogic.printAfter()]; aspect name 'myPojoAspectLogic'

  bean name  : org.springframework.context.annotation.ConfigurationClassPostProcessor.importAwareProcessor
  getClass() : class org.springframework.context.annotation.ConfigurationClassPostProcessor$ImportAwareBeanPostProcessor
  toString() : org.springframework.context.annotation.ConfigurationClassPostProcessor$ImportAwareBeanPostProcessor@265e65c6

-------------
taskA is running.
-------------
11111111 - anno :{a=a1, b=b1}
taskB is running.
222222222 - xml :{a=a1, b=b1}
-------------
```