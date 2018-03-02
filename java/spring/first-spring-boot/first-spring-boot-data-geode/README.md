


```bash
docker create                                       \
    --name my-geode                                 \
    -it                                             \
    --restart unless-stopped                        \
    -p 8080:8080                                    \
    -p 10334:10334                                  \
    -p 40404:40404                                  \
    -p 1099:1099                                    \
    -p 7070:7070                                    \
    -v /data0/store/geode:/data:rw                  \
    apachegeode/geode                               

docker start my-geode

docker exec -it my-geode sh
gfsh

start locator \
    --name=locator1

start server \
    --name=server1

create region \
    --name=regionA \
    --type=REPLICATE_PERSISTENT

# 如果是桌面环境，开启监控，
# 会自动打开浏览器访问 http://localhost:7070/pulse/login.html
# 用户名/密码 = admin/admin
start pulse

stop loacator \
    --name=locator1
```

## 参考

- [gfsh command help](http://geode.apache.org/docs/guide/14/tools_modules/gfsh/gfsh_command_index.html)
    - [start locator](http://geode.apache.org/docs/guide/14/tools_modules/gfsh/command-pages/start.html#topic_591260CF25D64562A0EDD7260D2AC6D4)
    - [start server](http://geode.apache.org/docs/guide/14/tools_modules/gfsh/command-pages/start.html#topic_3764EE2DB18B4AE4A625E0354471738A)
- [Geode in 5 minutes](https://cwiki.apache.org/confluence/display/GEODE/Index#Index-Geodein5minutes)

- spring-data-gemfire
    [reference](https://docs.spring.io/spring-data-gemfire/docs/current/reference/html/)
- [spring-gemfire-examples](https://github.com/spring-projects/spring-gemfire-examples)
- [spring-data-geode](https://github.com/spring-projects/spring-data-geode)
- [spring-boot-data-geode](https://github.com/spring-projects/spring-boot-data-geode)
- [spring-session-data-geode](https://github.com/spring-projects/spring-session-data-geode)
