

# 参考

- `org.springframework.cloud.sleuth.zipkin2.ZipkinAutoConfiguration`
- ZipkinProperties
- SamplerProperties


# 运行

```bash
# 参考 zipkin 的 docker-compose.yml
cd src/test/docker
docker-compose up
# 浏览器访问 http://localhost:9411
#docker-compose down

cd ${PROJECT_ROOT}
./gradlew bootRun

curl -v http://localhost:8080/a?p=1
# 1. curl返回： a[1]:b[1]:c[1]:S
#    请求头，相应头中并没有 trace 相关内容
#
# 2. 控制台日志
# /a 调用 /b
: http-outgoing-0 >> GET /b?p=1 HTTP/1.1
: http-outgoing-0 >> Accept: text/plain, text/plain, application/json, application/json, application/xml, application/*+json, application/*+json, text/xml, application/xml, application/*+xml, text/xml, application/*+xml, */*, */*
: http-outgoing-0 >> X-B3-TraceId: b841de2d1de2b3de
: http-outgoing-0 >> X-B3-SpanId: 5816f5438578331c
: http-outgoing-0 >> X-B3-Sampled: 1
: http-outgoing-0 >> X-Span-Name: http:/b
: http-outgoing-0 >> X-B3-ParentSpanId: b841de2d1de2b3de
: http-outgoing-0 >> Host: localhost:8080
: http-outgoing-0 >> Connection: Keep-Alive
: http-outgoing-0 >> User-Agent: Apache-HttpClient/4.5.3 (Java/1.8.0_144)
: http-outgoing-0 >> Accept-Encoding: gzip,deflate
: http-outgoing-0 >> "GET /b?p=1 HTTP/1.1[\r][\n]"
: http-outgoing-0 >> "Accept: text/plain, text/plain, application/json, application/json, application/xml, application/*+json, application/*+json, text/xml, application/xml, application/*+xml, text/xml, application/*+xml, */*, */*[\r][\n]"
: http-outgoing-0 >> "X-B3-TraceId: b841de2d1de2b3de[\r][\n]"
: http-outgoing-0 >> "X-B3-SpanId: 5816f5438578331c[\r][\n]"
: http-outgoing-0 >> "X-B3-Sampled: 1[\r][\n]"
: http-outgoing-0 >> "X-Span-Name: http:/b[\r][\n]"
: http-outgoing-0 >> "X-B3-ParentSpanId: b841de2d1de2b3de[\r][\n]"
: http-outgoing-0 >> "Host: localhost:8080[\r][\n]"
: http-outgoing-0 >> "Connection: Keep-Alive[\r][\n]"
: http-outgoing-0 >> "User-Agent: Apache-HttpClient/4.5.3 (Java/1.8.0_144)[\r][\n]"
: http-outgoing-0 >> "Accept-Encoding: gzip,deflate[\r][\n]"
: http-outgoing-0 >> "[\r][\n]"
    
    # /b 调用 /c
    : http-outgoing-1 >> GET /c?p=1 HTTP/1.1
    : http-outgoing-1 >> Accept: text/plain, text/plain, application/json, application/json, application/xml, application/*+json, application/*+json, text/xml, application/xml, application/*+xml, text/xml, application/*+xml, */*, */*
    : http-outgoing-1 >> X-B3-TraceId: b841de2d1de2b3de
    : http-outgoing-1 >> X-B3-SpanId: 62ada53469d5f10d
    : http-outgoing-1 >> X-B3-Sampled: 1
    : http-outgoing-1 >> X-Span-Name: http:/c
    : http-outgoing-1 >> X-B3-ParentSpanId: 5816f5438578331c
    : http-outgoing-1 >> Host: localhost:8080
    : http-outgoing-1 >> Connection: Keep-Alive
    : http-outgoing-1 >> User-Agent: Apache-HttpClient/4.5.3 (Java/1.8.0_144)
    : http-outgoing-1 >> Accept-Encoding: gzip,deflate
    : http-outgoing-1 >> "GET /c?p=1 HTTP/1.1[\r][\n]"
    : http-outgoing-1 >> "Accept: text/plain, text/plain, application/json, application/json, application/xml, application/*+json, application/*+json, text/xml, application/xml, application/*+xml, text/xml, application/*+xml, */*, */*[\r][\n]"
    : http-outgoing-1 >> "X-B3-TraceId: b841de2d1de2b3de[\r][\n]"
    : http-outgoing-1 >> "X-B3-SpanId: 62ada53469d5f10d[\r][\n]"
    : http-outgoing-1 >> "X-B3-Sampled: 1[\r][\n]"
    : http-outgoing-1 >> "X-Span-Name: http:/c[\r][\n]"
    : http-outgoing-1 >> "X-B3-ParentSpanId: 5816f5438578331c[\r][\n]"
    : http-outgoing-1 >> "Host: localhost:8080[\r][\n]"
    : http-outgoing-1 >> "Connection: Keep-Alive[\r][\n]"
    : http-outgoing-1 >> "User-Agent: Apache-HttpClient/4.5.3 (Java/1.8.0_144)[\r][\n]"
    : http-outgoing-1 >> "Accept-Encoding: gzip,deflate[\r][\n]"
    : http-outgoing-1 >> "[\r][\n]"
    : http-outgoing-1 << "HTTP/1.1 200 [\r][\n]"
    : http-outgoing-1 << "Content-Type: text/plain;charset=UTF-8[\r][\n]"
    : http-outgoing-1 << "Content-Length: 6[\r][\n]"
    : http-outgoing-1 << "Date: Thu, 30 Nov 2017 12:52:42 GMT[\r][\n]"
    : http-outgoing-1 << "[\r][\n]"
    : http-outgoing-1 << "c[1]:S"
    : http-outgoing-1 << HTTP/1.1 200 
    : http-outgoing-1 << Content-Type: text/plain;charset=UTF-8
    : http-outgoing-1 << Content-Length: 6
    : http-outgoing-1 << Date: Thu, 30 Nov 2017 12:52:42 GMT

: http-outgoing-0 << "HTTP/1.1 200 [\r][\n]"
: http-outgoing-0 << "Content-Type: text/plain;charset=UTF-8[\r][\n]"
: http-outgoing-0 << "Content-Length: 11[\r][\n]"
: http-outgoing-0 << "Date: Thu, 30 Nov 2017 12:52:42 GMT[\r][\n]"
: http-outgoing-0 << "[\r][\n]"
: http-outgoing-0 << "b[1]:c[1]:S"
: http-outgoing-0 << HTTP/1.1 200 
: http-outgoing-0 << Content-Type: text/plain;charset=UTF-8
: http-outgoing-0 << Content-Length: 11
: http-outgoing-0 << Date: Thu, 30 Nov 2017 12:52:42 GMT

# 3. http://localhost:9411 查询结果：可以看到 /a, /b, /c, 以及 MyService 的调用。
```