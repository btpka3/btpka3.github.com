package my.grails3.conf

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession
import redis.clients.jedis.JedisShardInfo

/**
 * 参考: http://docs.spring.io/spring-session/docs/1.3.0.BUILD-SNAPSHOT/reference/html5/#httpsession-redis
 *
 * @EnableRedisHttpSession 会注册一个 SessionRepositoryFilter。
 */
@Configuration
@EnableRedisHttpSession
class SpringSessionConf {

    @Bean
    public JedisConnectionFactory connectionFactory() {
        return new JedisConnectionFactory(new JedisShardInfo("redis://192.168.0.12:6379"));
    }

}
