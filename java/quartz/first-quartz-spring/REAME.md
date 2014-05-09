Quartz 集群的例子请参考
```
quartz-x.x..tar.gz
  |- examples/example13
```

`<<Spring Framework Reference Manual>>` 中给出的使用 Quartz 的例子比较适用于：
1. 要调度的 job 个数比较明确
2. 不会动态增加、停用


## FIXME
* 要集群的job无法与Quartz的API解耦？

## Best Practices?
* 自定义job应继承自org.springframework.scheduling.quartz.QuartzJobBean.
  之后就可以通过提供setter的方式，直接将jobDataMap中的同名数据通过setter设置进来？
  
* 自定义job应实现org.quartz.Job接口，配合SpringBeanJobFactory完成通过setter注入数据

* 应该使用 org.springframework.scheduling.quartz.SchedulerFactoryBean，以方便其他Bean引用Quartz的Scheduler实例

* 

```
  <task:executor id="taskExecutor" pool-size="10"/>

  <bean id="jobFactory"
        class="org.springframework.scheduling.quartz.SpringBeanJobFactory" />

  <bean class="org.springframework.scheduling.quartz.SchedulerFactoryBean">
    <property name="triggers">
      <list>
        <ref bean="xxxTrigger" /><!-- static knonwn job -->
      </list>
    </property>
    <property name="schedulerContextAsMap">
      <map>
        <entry key="service1"                  value-ref="remoteServiceProxy1"/>
      </map>
    </property>
    <property name="exposeSchedulerInRepository"  value="true" />               <!-- Spring管理的Scheduler加入Quartz集群 -->
    <property name="taskExecutor"                 ref="taskExecutor" />         <!-- org.quartz.threadPool.* -->
    <property name="schedulerName"                value="xxx" />                <!-- org.quartz.scheduler.instanceName -->
    <property name="dataSource"                   ref="xxx" />                  <!-- org.quartz.jobStore.dataSource, and will use JobStoreCMT subClass -->
    <property name="jobFactory"                   ref="jobFactory" />           <!-- org.quartz.scheduler.jobFactory.class -->
  </bean>

``` 

for real-time job scheduler, using [Obsidian Scheduler](http://www.obsidianscheduler.com/blog/feature-comparison-of-java-schedulers/)?

## Qu

```sh
sssssssssssssssss
```