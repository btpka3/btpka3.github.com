// Place your Spring DSL code here
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory

beans = {
    xmlns context: "http://www.springframework.org/schema/context"


    context."annotation-config"()
    redisHttpSessionConfiguration(RedisHttpSessionConfiguration)

    jedisConnectionFactory(JedisConnectionFactory) {
        hostName = "localhost"
        port = 6379
    }
}
