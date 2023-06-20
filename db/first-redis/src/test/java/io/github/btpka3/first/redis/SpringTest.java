package io.github.btpka3.first.redis;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Collections.singletonList;

/**
 * @author dangqian.zll
 * @date 2023/6/19
 * @see RedisTemplate
 * @see RedisAutoConfiguration
 * @see RedisProperties
 * @see RedisConnectionFactory
 * @see RedisStandaloneConfiguration
 * @see RedisSentinelConfiguration
 * @see RedisClusterConfiguration
 * @see org.springframework.data.redis.connection.MessageListener
 * @see RedisMessageListenerContainer
 * @see SubscriptionListener
 * @see MessageListenerAdapter
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
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration, clientConfiguration);
        jedisConnectionFactory.afterPropertiesSet();
        return jedisConnectionFactory;
    }


    public RedisTemplate<Object, Object> getRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.afterPropertiesSet();

        return template;
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


    public StringRedisTemplate getStringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate tpl = new StringRedisTemplate(redisConnectionFactory);
        tpl.afterPropertiesSet();
        return tpl;
    }

    protected RedisConnectionFactory getRedisConnectionFactory() {
        RedisProperties redisProperties = getRedisProperties();
        JedisClientConfiguration jedisClientConfiguration = getJedisClientConfiguration(redisProperties);
        RedisStandaloneConfiguration redisStandaloneConfiguration = getRedisStandaloneConfiguration(redisProperties);
        return getJedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }


    public <K, V> RedisTemplate<K, V> getRedisTemplate() {
        return (RedisTemplate<K, V>) getRedisTemplate(getRedisConnectionFactory());
    }

    public StringRedisTemplate getStringRedisTemplate() {
        return getStringRedisTemplate(getRedisConnectionFactory());
    }


    public RedisConnection getRedisConnection() {
        return getRedisConnectionFactory().getConnection();
    }


    @Test
    public void redisTemplate01() {

        StringRedisTemplate stringRedisTemplate = getStringRedisTemplate();

        ValueOperations<String, String> valueOp = stringRedisTemplate.opsForValue();
        System.out.println("spring:RedisTemplate:1: foo=" + valueOp.get("foo"));
        valueOp.set("foo", "bar-spring-RedisTemplate-" + System.currentTimeMillis());
        System.out.println("spring:RedisTemplate:2: foo=" + valueOp.get("foo"));

    }

    @Test
    public void pub01() {

        RedisConnection redisConnection = getRedisConnection();
        byte[] msg = "msg001".getBytes(StandardCharsets.UTF_8);
        byte[] channel = "channel001".getBytes(StandardCharsets.UTF_8);
        redisConnection.publish(msg, channel);
    }

    @Test
    public void pub02() {

        RedisTemplate redisTemplate = getStringRedisTemplate();
        redisTemplate.convertAndSend("channel001", "msg002");
    }

    @Test
    public void sub01() {

        RedisConnection redisConnection = getRedisConnection();
        byte[] channel = "channel001".getBytes(StandardCharsets.UTF_8);
        redisConnection.subscribe(
                (Message message, @Nullable byte[] pattern) -> {
                    System.out.println("sub02:msg:" + new String(message.getBody(), StandardCharsets.UTF_8));
                },
                channel
        );
        Assertions.assertTrue(redisConnection.isSubscribed());
        Subscription subscription = redisConnection.getSubscription();
        Assertions.assertNotNull(subscription);
    }

    public class MyMsgHandler {
        public void handleMsg(String text) {
            System.out.println("MyMsgHandler:msg:" + text);
        }
    }

    @Test
    public void sub02() {
        MyMsgHandler msgHandler = new MyMsgHandler();
        RedisConnectionFactory connectionFactory = getRedisConnectionFactory();

        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(msgHandler, "handleMsg");

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(messageListenerAdapter, ChannelTopic.of("channel001"));
        container.afterPropertiesSet();
    }

    /**
     * @see RedisStreamCommands#xAdd(MapRecord)
     */
    @Test
    public void streamAppend01() {
        RedisConnection con = getRedisConnection();
        byte[] stream = "stream001".getBytes(StandardCharsets.UTF_8);
        Map<byte[], byte[]> raw = new HashMap<>(4);
        {
            byte[] k = "a".getBytes(StandardCharsets.UTF_8);
            byte[] v = "a001".getBytes(StandardCharsets.UTF_8);
            raw.put(k, v);
        }
        {
            byte[] k = "b".getBytes(StandardCharsets.UTF_8);
            byte[] v = "b001".getBytes(StandardCharsets.UTF_8);
            raw.put(k, v);
        }
        ByteRecord record = StreamRecords.rawBytes(raw)
                .withStreamKey(stream);
        con.xAdd(record);
    }


    /**
     * @see StreamOperations#add(MapRecord)
     */
    @Test
    public void streamAppend02() {

        Map<String, String> raw = new HashMap<>(4);
        raw.put("a", "a002");
        raw.put("b", "b002");

        StringRecord record = StreamRecords.string(raw)
                .withStreamKey("my-stream");

        RedisTemplate redisTemplate = getStringRedisTemplate();
        StreamOperations streamOps = redisTemplate.opsForStream();
        streamOps.add(record);
    }

    @Test
    public void streamConsumer01() {
        RedisConnectionFactory connectionFactory = getRedisConnectionFactory();
        StreamListener<String, MapRecord<String, String, String>> streamListener = (MapRecord<String, String, String> record) -> {

        };

        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> containerOptions =
                StreamMessageListenerContainer.StreamMessageListenerContainerOptions.builder()
                        .pollTimeout(Duration.ofMillis(100))
                        .build();

        StreamMessageListenerContainer<String, MapRecord<String, String, String>> container = StreamMessageListenerContainer.create(
                connectionFactory,
                containerOptions
        );

        org.springframework.data.redis.stream.Subscription subscription = container.receive(StreamOffset.fromStart("my-stream"), streamListener);
    }

    @Test
    public void transaction01() {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(getRedisConnectionFactory());
        template.setEnableTransactionSupport(true);

        List<Object> txResults = template.execute(new SessionCallback<List<Object>>() {
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.opsForSet().add("key", "value1");

                // This will contain the results of all operations in the transaction
                return operations.exec();
            }
        });
        System.out.println("Number of items added to set: " + txResults.get(0));

    }


    protected RedisScript<Long> redisScript = RedisScript.of(
            new ClassPathResource("io/github/btpka3/first/redis/incr.lua"),
            Long.class
    );

    @Test
    public void script01() {


        RedisTemplate<Object, Object> redisTemplate = getRedisTemplate();
        String key = "key001";
        redisTemplate.delete(key);

        Long currentValue = incr("key001", 3L, 2L, 60L);

        System.out.println("currentValue = " + currentValue);
        Assertions.assertEquals(5, currentValue);
        long ttl = redisTemplate.getExpire(key);
        System.out.println("ttl = " + ttl);
        Assertions.assertTrue(ttl > 0 && ttl <= 60);
    }

    protected Long incr(String key, Long longIncrement, Long longDefaultValue, Long expireDurationInSeconds) {
        RedisTemplate<Object, Object> redisTemplate = getRedisTemplate();
        return redisTemplate.execute(
                redisScript,
                singletonList(key),
                longIncrement.toString(),
                longDefaultValue.toString(),
                expireDurationInSeconds.toString()
        );
    }

    int MAX_KEY_COUNT = 10000;
    int MAX_INCR_COUNT_PER_KEY = 10;


    @Test
    @SneakyThrows
    public void scriptTestMultiThread01() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(100);
        executor.setMaxPoolSize(100);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("redis-script-task-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.setQueueCapacity(MAX_KEY_COUNT * MAX_INCR_COUNT_PER_KEY);
        executor.initialize();

        deleteAllKeys();

        long startTime = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(MAX_KEY_COUNT * MAX_INCR_COUNT_PER_KEY);
        for (int keyCount = 0; keyCount < MAX_KEY_COUNT; keyCount++) {
            for (int incrCount = 0; incrCount < MAX_INCR_COUNT_PER_KEY; incrCount++) {
                String key = "k-" + keyCount;
                Long longIncrement = 3L;
                Long longDefaultValue = 2L;
                Long expireDurationInSeconds = 60 * 5L;

                executor.execute(() -> {
                    incr(key, longIncrement, longDefaultValue, expireDurationInSeconds);
                    countDownLatch.countDown();
                });
            }
        }
        countDownLatch.await();
        long endTime = System.currentTimeMillis();
        long cost = endTime - startTime;

        checkAllKeys();
        System.out.println("==============Done. cost=" + cost);
        /* result
        =============== valueSuccessCount=10000, valueErrKeys=[]
        ==============Done. cost=97122
        */
    }

    protected void deleteAllKeys() {
        RedisTemplate<Object, Object> redisTemplate = getRedisTemplate();
        for (int keyCount = 0; keyCount < MAX_KEY_COUNT; keyCount++) {
            String key = "k-" + keyCount;
            redisTemplate.delete(key);
        }
    }

    protected void checkAllKeys() {
        String expectedValue = "32";
        AtomicLong valueSuccessCount = new AtomicLong(0);
        List<String> valueErrKeys = new ArrayList<>(10);
        RedisTemplate<Object, Object> redisTemplate = getRedisTemplate();
        for (int keyCount = 0; keyCount < MAX_KEY_COUNT; keyCount++) {
            String key = "k-" + keyCount;
            String value = (String) redisTemplate.opsForValue().get(key);

            if (Objects.equals(expectedValue, value)) {
                valueSuccessCount.addAndGet(1);
            } else {
                if (valueErrKeys.size() <= 16) {
                    valueErrKeys.add(key);
                }
            }
        }
        System.out.println("=============== valueSuccessCount=" + valueSuccessCount + ", valueErrKeys=" + valueErrKeys);
    }

//    protected void incr(RedisOperations ops, Object key, Long value, Long defaultValue, int expiredTime) {
//        ops.watch(key);
//        DataType dataType = ops.type(key);
//        if (Objects.equals(DataType.STRING, dataType)) {
//            throw new RuntimeException("Only `String` type is suuported `incr`, " +
//                    "but found key=" + key + "'s data type is `" + dataType + "`");
//        }
//        ops.multi();
//        ops.opsForValue().increment(key, value);
//        List<Object> list = ops.exec();
//
//
//    }


}
