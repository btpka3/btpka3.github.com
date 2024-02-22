
# see
- org.apache.flink.examples.java.wordcount.WordCount
# 单机版
https://nightlies.apache.org/flink/flink-docs-release-1.8/

```bash
# @终端1
docker run \
    -it \
    --rm \
    --name my-flink \
    -p 8081:8081 \
    flink:1.3.2-hadoop27-scala_2.11-alpine \
    local

# @终端2    
nc -l 12345 

# @终端3
docker cp \
    /Users/zll/.gradle/caches/modules-2/files-2.1/org.apache.flink/flink-examples-streaming_2.11/1.3.2/e16b28205a673d628f6c7c620fc066572507f8fd/flink-examples-streaming_2.11-1.3.2.jar \
    my-flink:/tmp

docker exec my-flink ls -l /tmp

docker exec \
    -it  \
    my-flink \
    flink run \
        -c org.apache.flink.streaming.examples.socket.SocketWindowWordCount \
        /tmp/flink-examples-streaming_2.11-1.3.2.jar \
        --hostname 192.168.0.41 \
        --port 12345
    
# 浏览器访问  http://localhost:8081/     
```

# 集群版

```bash
# @终端1
cd src/test/docker
docker-compose up

# @终端2    
nc -l 12345 

# @终端3
docker ps
docker cp \
    /Users/zll/.gradle/caches/modules-2/files-2.1/org.apache.flink/flink-examples-streaming_2.11/1.3.2/e16b28205a673d628f6c7c620fc066572507f8fd/flink-examples-streaming_2.11-1.3.2.jar \
    docker_taskmanager_1:/tmp

docker exec \
    -it  \
    docker_taskmanager_1 \
    flink run \
        -c org.apache.flink.streaming.examples.socket.SocketWindowWordCount \
        /tmp/flink-examples-streaming_2.11-1.3.2.jar \
        --hostname 192.168.0.41 \
        --port 12345

# 浏览器访问  http://localhost:8081/  
```