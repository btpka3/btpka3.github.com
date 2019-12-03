

## 目的
- 学习 flowable
    - 建模
    - 自定义服务传参、调用 
    - 并发异步执行
    - 作为嵌入式服务使用

## 参考
- [Flowable](https://www.flowable.org/)
- [spring integration](https://www.flowable.org/docs/userguide/index.html#springintegration)

## 术语
- BPMN  : [Business Process Model and Notation](http://www.bpmn.org/)
- CMMN  : [Case Management Model and Notation](https://www.omg.org/spec/CMMN/)
- DMN   : [Decision Model and Notation](https://www.omg.org/spec/DMN/1.1)


## 组件

- [flowable-idm](http://localhost:8080/flowable-idm/) : 用户、用户组、权限管理   
- [flowable-modeler](http://localhost:8888/flowable-modeler/) : 
- [flowable-admin](http://localhost:9988/flowable-admin/)
- [flowable-task](http://localhost:9999/flowable-task/)
 

## TODO

- flowable-idm  ：
    - 如何让 flowable-idm 自动通过 API 自动实时调用远程数据？或者退而求其次，如何 将自己的用户、权限信息导入到  flowable-idm
- flowable-modeler
    - 如何 处理 "第N级主管审批"？
    - 如何加入自定类型的用户？