// Place your Spring DSL code here


import me.test.RedisSerializerImpl
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration

beans = {
    xmlns context: "http://www.springframework.org/schema/context"

    def cfg = application.config

    // Spring Session
    context."annotation-config"()
    redisHttpSessionConfiguration(RedisHttpSessionConfiguration)

    jedisConnectionFactory(JedisConnectionFactory) {
        hostName = "localhost"
        port = 6379
    }

    if (cfg.springSession.fixClassLoader) {
        stringRedisSerializer(StringRedisSerializer)
        defaultRedisSerializer(RedisSerializerImpl)
        sessionRedisTemplate(RedisTemplate) {
            connectionFactory = ref("jedisConnectionFactory")
            keySerializer = ref("stringRedisSerializer")
            hashKeySerializer = ref("stringRedisSerializer")
            defaultSerializer = ref("defaultRedisSerializer")
        }
    }

    // Spring Security
    springConfig.addAlias 'userDetailsService', 'myUserDetailsService'

}
