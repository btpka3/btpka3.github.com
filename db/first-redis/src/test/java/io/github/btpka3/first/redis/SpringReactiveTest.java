package io.github.btpka3.first.redis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.ReactiveSubscription.Message;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.ReactiveRedisMessageListenerContainer;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.stream.StreamReceiver;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * @author dangqian.zll
 * @date 2023/6/19
 * @see RedisTemplate
 * @see ReactiveRedisTemplate
 * @see RedisAutoConfiguration
 * @see RedisReactiveAutoConfiguration
 * @see RedisProperties
 * @see RedisConnectionFactory
 * @see RedisStandaloneConfiguration
 * @see RedisSentinelConfiguration
 * @see RedisClusterConfiguration
 * @see MessageListener
 * @see RedisMessageListenerContainer
 * @see SubscriptionListener
 * @see MessageListenerAdapter
 */
public class SpringReactiveTest {


    protected RedisProperties getRedisProperties() {

        RedisProperties properties = new RedisProperties();
        properties.setHost("localhost");
        properties.setPort(6379);
        properties.setUsername(null);
        properties.setPassword(null);
        properties.setSsl(false);
        return properties;
    }

    protected LettuceClientConfiguration getLettuceClientConfiguration() {
        return LettuceClientConfiguration.builder()
                .useSsl()
                .and()
                .commandTimeout(Duration.ofSeconds(2))
                .shutdownTimeout(Duration.ZERO)
                .build();

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


    public RedisSerializationContext getRedisSerializationContext() {
        JdkSerializationRedisSerializer jdkSerializer = new JdkSerializationRedisSerializer();
        return RedisSerializationContext
                .newSerializationContext()
                .key(jdkSerializer)
                .value(jdkSerializer)
                .hashKey(jdkSerializer)
                .hashValue(jdkSerializer)
                .build();
    }

    public <K, V> ReactiveRedisTemplate<K, V> getReactiveRedisTemplate(
            ReactiveRedisConnectionFactory connectionFactory,
            RedisSerializationContext<K, V> serializationContext
    ) {
        return new ReactiveRedisTemplate<K, V>(connectionFactory, serializationContext);
    }


    protected ReactiveRedisConnectionFactory getReactiveRedisConnectionFactory() {
        RedisProperties redisProperties = getRedisProperties();
        RedisStandaloneConfiguration redisStandaloneConfiguration = getRedisStandaloneConfiguration(redisProperties);
        LettuceClientConfiguration clientConfig = getLettuceClientConfiguration();
        return new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
    }


    public <K, V> ReactiveRedisTemplate<K, V> getReactiveRedisTemplate() {
        ReactiveRedisConnectionFactory connectionFactory = getReactiveRedisConnectionFactory();
        RedisSerializationContext<K, V> redisSerializationContext = getRedisSerializationContext();
        return getReactiveRedisTemplate(connectionFactory, redisSerializationContext);
    }

    @Test
    public void value01() {

        ReactiveRedisTemplate tpl = getReactiveRedisTemplate();
        tpl.opsForValue()
                .set("foo", "bar-spring-ReactiveRedisTemplate-" + System.currentTimeMillis());

    }


    @Test
    public void pub01() {
        ReactiveRedisTemplate tpl = getReactiveRedisTemplate();
        tpl.convertAndSend("channel001", "msg002");

    }


    @Test
    public void sub01() {

        ReactiveRedisConnectionFactory factory = getReactiveRedisConnectionFactory();
        ReactiveRedisMessageListenerContainer container = new ReactiveRedisMessageListenerContainer(factory);

        Flux<Message<String, String>> stream = container.receive(ChannelTopic.of("my-channel"));

    }

    @Test
    public void streamConsumer02() {
        ReactiveRedisConnectionFactory connectionFactory = getReactiveRedisConnectionFactory();

        StreamReceiver.StreamReceiverOptions<String, MapRecord<String, String, String>> options = StreamReceiver.StreamReceiverOptions.builder()
                .pollTimeout(Duration.ofMillis(100))
                .build();


        StreamReceiver<String, MapRecord<String, String, String>> receiver = StreamReceiver.create(connectionFactory, options);

        Flux<MapRecord<String, String, String>> messages = receiver.receive(StreamOffset.fromStart("my-stream"));
    }


}
