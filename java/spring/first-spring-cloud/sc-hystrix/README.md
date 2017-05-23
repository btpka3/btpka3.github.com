# Hystrix



```bash
# 浏览器访问： http://localhost:8080/hystrix

curl -v http://localhost:8080/hystrix.stream
# Content-Type: text/event-stream;charset=UTF-8

curl -v http://localhost:8080/turbine.stream


http://localhost:8080/call 

curl -v http://localhost:8080/test/test01
{"data":"DemoService#doIt : [a:aaa, b:bbb]"}

curl -v http://localhost:8080/test/test02
{"data":"DemoService#doItFallback : [a:aaa, b:bbb, err:111]"}
```
