

# 参考

- [Implementing Your Own Operators](https://github.com/ReactiveX/RxJava/wiki/Implementing-Your-Own-Operators)
- [《Reactive Programming with RxJava》](http://www.allitebooks.com/reactive-programming-with-rxjava/)

# ObserveOn 和 SubscribeOn
 
|                                                           |observerOn|subscribeOn|
|-----------------------------------------------------------|----------|-----------|
|可调用次数                                                  |多次       |1次  |
|让 ObservableSource 创建元素/消息时，在指定的 Scheduler 上执行 |是         |否   |
|让 观察者/Subscriber 在执行各种操作时，在指定的 Scheduler 上执行|是         |是   |

 
 
 # 图片说明：
 
 ![a](http://reactivex.io/documentation/operators/images/schedulers.png)
 
 
 # 总结
 
- 确定问题点：通过声明 Flowable 变量，多次 订阅，其中一个订阅用来打印 debug 信息，以便确认问题——比如 onComplete 没有？

