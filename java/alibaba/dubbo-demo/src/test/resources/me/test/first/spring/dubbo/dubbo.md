


# 验证 dubbo 基础能力

```shell
# 通常只执行一次，虚拟一个完全隔离的虚拟机。因为 docker 所需的 Linux Kernel相关工鞥 MacOs 不支持。
# 如果遇到 docker machine start 报错 255 且无法搞定，就这样重来一次。
podman machine init  
podman machine rm

podman machine start --log-level debug
# 先启动 zookeeper (单实例）
cd gong9-mw-impl-cloud/src/test/resources/com/alibaba/security/gong9/mw/dubbo
podman-compose build
podman-compose up -d --force-recreate --renew-anon-volumes

# 启动自定义 Dubbo 服务实现 ： 运行单测：DemoDubboServiceConsumerTest
# 浏览器访问 dubbo admin :  `http://localhost:38080/`, 验证服务已经注册。 

# 启动自定义 Dubbo 服务消费者进行验证  ： 运行单测：DemoDubboServiceConsumerTest

# 测试验证完成后，停止的 zookeeper
podman-compose down
podman machine stop

```


# 内部网络问题调试
```shell
podman container ls
podman exec -it dubbo_dubbo-admin_1 sh
apt update
apt install iputils-ping
# 可以直接使用 docker compose 中的 service name 进行 ping
ping zoo1
PING zoo1.dns.podman (10.89.0.11) 56(84) bytes of data.
64 bytes from 10.89.0.11 (10.89.0.11): icmp_seq=1 ttl=64 time=0.141 ms
```
