
# 参考

- [使用 Collectd + InfluxDB + Grafana 监控主机](http://blog.csdn.net/caodanwang/article/details/51967379)
- [利用Metrics+influxdb+grafana构建监控平台](http://www.jianshu.com/p/fadcf4d92b0e)

# 启动

```bash
docker-compose -f src/test/docker/docker-compose.yml down 
docker-compose -f src/test/docker/docker-compose.yml up --build 
```

# 验证

## 验证 influxdb 

```bash
# 应当有两个数据库：_internal 和 collectd
docker exec -it docker_influxdb_1 influx -execute "show databases"
# 数据库 collectd 中有 cpu_value 等多张表
docker exec -it docker_influxdb_1 influx -database collectd -execute "show measurements"
docker exec -it docker_influxdb_1 influx -database collectd -execute "SHOW TAG KEYS FROM cpu_value"
docker exec -it docker_influxdb_1 influx -database collectd -execute "SHOW FIELD KEYS FROM cpu_value"
docker exec -it docker_influxdb_1 influx -database collectd -execute "SHOW SERIES FROM cpu_value"
docker exec -it docker_influxdb_1 influx -database collectd -execute "SELECT * FROM cpu_value"
curl -G 'http://localhost:8086/query?pretty=true' \
    --data-urlencode "db=collectd" \
    --data-urlencode 'q=SHOW SERIES FROM cpu_value'
```

## 验证 Grafana 
-  浏览器访问 http://localhost:3000/
-  添加 Datasource:

    ```text
    Name             : test     // 随便起名
    Type             : InfluxDB
    HTTP Settings    : URL      : http://influxdb:8086
    HTTP Settings    : Access   : proxy
    InfluxDB Details : Database : collectd
    InfluxDB Details : User     : <Empty>
    InfluxDB Details : Password : <Empty>
    ``` 

- 添加 Dashboard:
    - 添加 Graph
    - 点击 "Panel Title" : Edit
    - Metrics : 
    
        ```txt
        FROM        : default, cpu_value
        SELECT      : field(value), fill(null)
        GROUP BY    : time($__interval), fill(null)
        FORMAT AS   : Time series 
        ```