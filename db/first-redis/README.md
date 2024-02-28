

- docker hub : [_/redis](https://hub.docker.com/_/redis/tags)
- docker hub : [bitnami/redis](https://hub.docker.com/r/bitnami/redis/tags)
- k8s chart : [Bitnami package for Redis(R)](https://github.com/bitnami/charts/blob/main/bitnami/redis/README.md)


```shell
docker run --rm -p 6379:6379  docker.io/library/redis:7.2-alpine

docker run --rm docker.io/library/alpine:3.17.3 date
```


# class 
- org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
- org.springframework.boot.autoconfigure.data.redis.RedisProperties