

# ObserveOn 和 SubscribeOn
 
|                                                           |observerOn|subscribeOn|
|-----------------------------------------------------------|----------|-----------|
|可调用次数                                                  |多次       |1次  |
|让 ObservableSource 创建元素/消息时，在指定的 Scheduler 上执行 |是         |否   |
|让 观察者/Subscriber 在执行各种操作时，在指定的 Scheduler 上执行|是         |是   |

 
 
 # 图片说明：
 
 ![a](http://reactivex.io/documentation/operators/images/schedulers.png)
 
 
 