

```text
curl -v http://localhost:8080/
http://localhost:8080/webjars/swagger-ui/3.2.2/index.html
curl -v http://localhost:10090/pay/rs/local/14100/api/swagger.json

http://localhost:10090/pay/rs/local/14100/webjars/swagger-ui/3.0.19/index.html?url=http://localhost:10090/pay/rs/local/14100/api/swagger.json


./gradlew assemble

cd src/test/docker
docker-compose up
#docker-compose stop



docker exec -it docker_s5_1 sh
    # docker 容器内测试以下命令
    apk add --no-cache curl
    curl  http://172.19.0.2:8081/   # OK
    curl  https://www.baidu.com/    # OK
    curl --proxy socks5://zhang3:zhang3@172.19.0.2:1080 http://172.19.0.2:8081/

# 宿主机内执行以下测试
curl  http://172.19.0.2:8081/       # ERROR, 172的IP是容器内IP。外部无法访问
                                    # OK
curl --proxy socks5://zhang3:zhang3@localhost:1080 http://172.19.0.2:8081/

# 确保 content-type 设置OK : 'application/x-ns-proxy-autoconfig'
curl -v http://localhost:8081/s5.pac 
# MacOS设置系统代理为 `http://localhost:8081/s5.pac`  # 注意：直接设置本地文件系统的路径并不成功。
# 然后浏览器直接访问 `http://172.19.0.2:8081/`应该 OK


ssh zll@192.168.0.41 -C -N -g -L 1080:localhost:1080  
```