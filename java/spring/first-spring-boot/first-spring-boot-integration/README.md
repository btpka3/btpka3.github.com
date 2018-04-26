

## 启动

```bash
docker-compose -f first-spring-boot-integration/src/test/docker/docker-compose.yml up
```

## 参考
- [spring-integration-jdbc-lock](https://github.com/vpavic/spring-integration-jdbc-lock)
- [spring-integration-samples](https://github.com/spring-projects/spring-integration-samples)


## 笔记
```text
@IntegrationAutoConfiguration
@EnableIntegration
@EnablePublisher
@GlobalChannelInterceptor
@IntegrationConverter
@ServiceActivator



MessageProducer#getOutputChannel()
    MessageChannel#send(Message)

MessageSource#receive()


ChannelInterceptor
    ExecutorChannelInterceptor
        ThreadStatePropagationChannelInterceptor
    ChannelInterceptorAdapter
    ImmutableMessageChannelInterceptor


    
    

IntegrationConsumer
    AbstractPollingEndpoint
        PollingConsumer
        SourcePollingChannelAdapter
    EventDrivenConsumer

#AbstractMessageProcessingTransformer

AbstractEndpoint
    AbstractPollingEndpoint
    EventDrivenConsumer
    GatewayProxyFactoryBean,
    JmsMessageDrivenEndpoint 
    MessageProducerSupport
    MessagingGatewaySupport

AbstractMessageHandler
```

## MessageStore

```text
AbstractBatchingMessageGroupStore
    AbstractMessageGroupStore
        AbstractKeyValueMessageStore
            GemfireMessageStore
            RedisMessageStore
        JdbcMessageStore
        AbstractConfigurableMongoDbMessageStore
            ConfigurableMongoDbMessageStore
            MongoDbChannelMessageStore
        JdbcMessageStore
        MongoDbMessageStore
        SimpleMessageStore
```

## MessageSource

```text

MessageSource                               // 从哪里获取消息
    AbstractMessageSource
        AbstractInboundFileSynchronizingMessageSource
            FtpInboundFileSynchronizingMessageSource
            SftpInboundFileSynchronizingMessageSource
        AbstractRemoteFileStreamingMessageSource
            FtpStreamingMessageSource
            SftpStreamingMessageSource
        AttributePollingMessageSource       // 获取 JMX 属性
        
            
    ByteStreamReadingMessageSource          // 从 InputStream 中读取消息
    CharacterStreamReadingMessageSource     // 从 Readers 中读取消息
    CorrelatingMessageBarrier   
    DirectMessageReceivingMessageSource     // 从 Twitter 获取消息
    ExpressionEvaluatingMessageSource
    FeedEntryMessageSource
    FileReadingMessageSource                // 从文件读取消息
    
   
    JdbcPollingChannelAdapter               // 从数据库查询
    JmsDestinationPollingSource             // 从 JMS 轮训消息
    JpaPollingChannelAdapter                // 从各种类 SQL 查询消息
    MailReceivingMessageSource              // 从邮箱中读取消息
    MBeanTreePollingMessageSource
    MentionsReceivingMessageSource          // 从 Twitter 获取消息
    MethodInvokingMessageSource             // 方法调用
    MongoDbMessageSource                    // 从 MongoDb 中查询作为消息
    RedisStoreMessageSource                 // 从 Redis 中查询作为消息
    ResourceRetrievingMessageSource         // 从 spring resources 读取消息
    ScriptExecutingMessageSource            // 
    SearchReceivingMessageSource            // 从 Twitter 获取消息
    StoredProcPollingChannelAdapter
    TimelineReceivingMessageSource
```

## MessageHandler

```text
# 核心对象是 spring framework 中提供的
org.springframework.messaging.MessageHandler

AbstractBrokerMessageHandler
    SimpleBrokerMessageHandler
    StompBrokerRelayMessageHandler

AbstractMethodMessageHandler
    SimpAnnotationMethodMessageHandler
        WebSocketAnnotationMethodMessageHandler

SubProtocolWebSocketHandler
UserDestinationMessageHandler
UserRegistryMessageHandler

# 然后 spring-integration 对其进行了扩展。
org.springframework.integration.handler.AbstractMessageHandler
    AbstractMessageRouter
        AbstractMappingMessageRouter
            PayloadTypeRouter
            HeaderValueRouter
            ErrorMessageExceptionTypeRouter
            ExpressionEvaluatingRouter
            MethodInvokingRouter
            XPathRouter
        RecipientListRouter
```

## MessageChannel

```text

MessageChannel              // 发送消息的通道
    AbstractMessageChannel

    PollableChannel         // 有缓存消息功能的 channel，只能阻塞地接收消息（有 receive 方法）
        NullChannel
        AbstractPollableChannel
            QueueChannel            // 消息 FIFO
                PriorityChannel     // 消息先按优先级，在按 FIFO
                RendezvousChannel   // 消息如果没有被接收，会阻塞消息发送方

    SubscribableChannel                     // 以订阅模式，异步的接收消息，通过 MessageHandler 处理消息
        AbstractSubscribableChannel
            DirectChannel                   // 点对点，异步接收消息
            AbstractExecutorChannel
                ExecutorChannel             // 与 DirectChannel 类似，但对接到 TaskExecutor
                ExecutorSubscribableChannel // 
                PublishSubscribeChannel     // 发布-订阅模式。

        FixedSubscriberChannel              


    
    // FixedSubscriberChannelPrototype
    // MessageChannelReference
```


## MessageProducer

```text

MessageProducer

AbstractMessageProducingHandler
    MessageHandlerChain
    AbstractCorrelatingMessageHandler
        AggregatingMessageHandler
        ResequencingMessageHandler
    AbstractReplyProducingMessageHandler
        AbstractAmqpOutboundEndpoint
            AmqpOutboundEndpoint
            AsyncAmqpOutboundGateway
        AbstractMessageSplitter
            FileSplitter
            DefaultMessageSplitter
            ExpressionEvaluatingSplitter
            MethodInvokingSplitter
            XPathMessageSplitter
        AbstractReplyProducingPostProcessingMessageHandler
            MessageFilter
        AbstractWebServiceOutboundGateway
            MarshallingWebServiceOutboundGateway
            SimpleWebServiceOutboundGateway
        AbstractRemoteFileOutboundGateway
            FtpOutboundGateway
            SftpOutboundGateway
        BarrierMessageHandler
        BridgeHandler
        ContentEnricher
        FileWritingMessageHandler
        DelayHandler
        HttpRequestExecutingMessageHandler
        JdbcOutboundGateway
        JmsOutboundGateway
        JpaOutboundGateway
        MessageTransformingHandler
        RedisOutboundGateway
        RedisQueueOutboundGateway
        OperationInvokingMessageHandler
        RmiOutboundGateway
        ScatterGatherHandler
        ServiceActivatingHandler
        StoredProcOutboundGateway
        TcpOutboundGateway
        TwitterSearchOutboundGateway

MessageProducerSupport
    AbstractInternetProtocolReceivingChannelAdapter
        UnicastReceivingChannelAdapter
            MulticastReceivingChannelAdapter
    AbstractMqttMessageDrivenChannelAdapter
        MqttPahoMessageDrivenChannelAdapter
    FileTailingMessageProducerSupport
        ApacheCommonsFileTailingMessageProducer
        OSDelegatingFileTailingMessageProducer
    StompInboundChannelAdapter
    ExpressionMessageProducerSupport
        ApplicationEventListeningMessageProducer
        CacheListeningMessageProducer
        ContinuousQueryMessageProducer
    ImapIdleChannelAdapter
    AbstractXmppConnectionAwareEndpoint
        ChatMessageListeningEndpoint
        PresenceListeningEndpoint
    AmqpInboundChannelAdapter
    NotificationListeningMessageProducer
    RedisInboundChannelAdapter
    RedisQueueMessageDrivenEndpoint
    SyslogReceivingChannelAdapterSupport
        TcpSyslogReceivingChannelAdapter
        UdpSyslogReceivingChannelAdapter
    TcpConnectionEventListeningMessageProducer
    TcpReceivingChannelAdapter
    WebSocketInboundChannelAdapter

```