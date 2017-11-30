# Hystrix


Hystrix 可以做到自定义的容错处理，比如要调用的远程服务出错，就用本地的替代逻辑处理。


```bash
# 浏览器访问： http://localhost:10040/hystrix

curl -v http://localhost:10040/hystrix.stream
# Content-Type: text/event-stream;charset=UTF-8

curl -v http://localhost:10040/turbine.stream

# 正常流程，未出错。
curl -v http://localhost:10040/test/test01
{"data":"DemoService#doIt : [a:aaa, b:bbb]"}

# 内部会抛出错误，然后被处理后返回
curl -v http://localhost:10040/test/test02
{"data":"DemoService#doItFallback : [a:aaa, b:bbb, err:111]"}
```
