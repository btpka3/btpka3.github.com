package io.github.btpka3.first.redis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * @author dangqian.zll
 * @date 2023/6/19
 * @see RedisAutoConfiguration
 * @see RedisProperties
 * @see RedisConnectionFactory
 * @see RedisStandaloneConfiguration
 * @see RedisSentinelConfiguration
 * @see RedisClusterConfiguration
 */
public class SpringTest {


    protected RedisProperties getRedisProperties() {

        RedisProperties properties = new RedisProperties();
        properties.setHost("localhost");
        properties.setPort(6379);
        properties.setUsername(null);
        properties.setPassword(null);
        properties.setSsl(false);
        return properties;
    }

    protected JedisClientConfiguration getJedisClientConfiguration(
            RedisProperties properties
    ) {
        JedisClientConfiguration.JedisClientConfigurationBuilder builder = JedisClientConfiguration.builder();

        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(properties.isSsl()).whenTrue().toCall(builder::useSsl);
        map.from(properties.getTimeout()).to(builder::readTimeout);
        map.from(properties.getConnectTimeout()).to(builder::connectTimeout);
        map.from(properties.getClientName()).whenHasText().to(builder::clientName);
        return builder.build();
    }

    protected RedisStandaloneConfiguration getRedisStandaloneConfiguration(RedisProperties properties) {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(properties.getHost());
        config.setPort(properties.getPort());
        config.setUsername(properties.getUsername());
        config.setPassword(RedisPassword.of(properties.getPassword()));
        config.setDatabase(properties.getDatabase());
        return config;
    }

    protected JedisConnectionFactory getJedisConnectionFactory(
            RedisStandaloneConfiguration redisStandaloneConfiguration,
            JedisClientConfiguration clientConfiguration
    ) {
        JedisConnectionFactory  jedisConnectionFactory= new JedisConnectionFactory(redisStandaloneConfiguration, clientConfiguration);
        jedisConnectionFactory.afterPropertiesSet();
        return jedisConnectionFactory;
    }

    public RedisTemplate<Object, Object> getRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    public StringRedisTemplate getStringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new StringRedisTemplate(redisConnectionFactory);
    }

    public RedisTemplate<Object, Object> getRedisTemplate() {
        RedisProperties redisProperties = getRedisProperties();
        JedisClientConfiguration jedisClientConfiguration = getJedisClientConfiguration(redisProperties);
        RedisStandaloneConfiguration redisStandaloneConfiguration = getRedisStandaloneConfiguration(redisProperties);
        RedisConnectionFactory redisConnectionFactory = getJedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
        return getRedisTemplate(redisConnectionFactory);
    }

    public StringRedisTemplate getStringRedisTemplate() {
        RedisProperties redisProperties = getRedisProperties();
        JedisClientConfiguration jedisClientConfiguration = getJedisClientConfiguration(redisProperties);
        RedisStandaloneConfiguration redisStandaloneConfiguration = getRedisStandaloneConfiguration(redisProperties);
        RedisConnectionFactory redisConnectionFactory = getJedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
        return getStringRedisTemplate(redisConnectionFactory);
    }


    @Test
    public void redisTemplate01() {

        StringRedisTemplate stringRedisTemplate = getStringRedisTemplate();

        ValueOperations<String, String> valueOp = stringRedisTemplate.opsForValue();
        System.out.println("spring:RedisTemplate:1: foo=" + valueOp.get("foo"));
        valueOp.set("foo", "bar-spring-RedisTemplate-" + System.currentTimeMillis());
        System.out.println("spring:RedisTemplate:2: foo=" + valueOp.get("foo"));

    }
}
