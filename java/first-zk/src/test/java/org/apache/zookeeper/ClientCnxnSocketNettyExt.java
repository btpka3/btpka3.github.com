package org.apache.zookeeper;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.proxy.Socks5ProxyHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.apache.zookeeper.client.ZKClientConfig;
import org.apache.zookeeper.common.ClientX509Util;
import org.apache.zookeeper.common.NettyUtils;
import org.apache.zookeeper.common.StringUtils;
import org.apache.zookeeper.common.X509Exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * FIXME: {@link ClientCnxnSocketNetty} 是非public的，故无法使用自定义的package 的子类来继承实现扩展，且其父类 {@link ClientCnxnSocket} 也不是 public 的。
 * 那只能先这样tricky 的方式（使用这个java package）来搞了。
 *
 * 该类做了以下修改：
 * - copy {@link ClientCnxnSocketNetty} 的源码，并修改类名
 * - 新增 {@link #configureBootstrap(Bootstrap)} 方法，以便对 netty 进行自定义配置。
 * - 修改 {@link #LOG} 使用的名称
 * - 增加 {@link org.apache.zookeeper.ClientCnxnSocketNettyExt.ZKClientPipelineFactory#initProxy(io.netty.channel.ChannelPipeline)} 方法，并调用。
 *
 * @author dangqian.zll
 * @date 2025/10/15
 */
public class ClientCnxnSocketNettyExt extends ClientCnxnSocket {

    private static final Logger LOG = LoggerFactory.getLogger(ClientCnxnSocketNettyExt.class);

    private final EventLoopGroup eventLoopGroup;
    private Channel channel;
    private CountDownLatch firstConnect;
    private ChannelFuture connectFuture;
    private final Lock connectLock = new ReentrantLock();
    private final AtomicBoolean disconnected = new AtomicBoolean();
    private final AtomicBoolean needSasl = new AtomicBoolean();
    private final Semaphore waitSasl = new Semaphore(0);

    private static final AtomicReference<ByteBufAllocator> TEST_ALLOCATOR = new AtomicReference<>(null);

    public ClientCnxnSocketNettyExt(ZKClientConfig clientConfig) throws IOException {
        this.clientConfig = clientConfig;
        // Client only has 1 outgoing socket, so the event loop group only needs
        // a single thread.
        eventLoopGroup = NettyUtils.newNioOrEpollEventLoopGroup(1 /* nThreads */);
        initProperties();
    }

    /**
     * lifecycles diagram:
     * <p>
     * loop:
     * - try:
     * - - !isConnected()
     * - - - connect()
     * - - doTransport()
     * - catch:
     * - - cleanup()
     * close()
     * <p>
     * Other non-lifecycle methods are in jeopardy getting a null channel
     * when calling in concurrency. We must handle it.
     */

    @Override
    boolean isConnected() {
        // Assuming that isConnected() is only used to initiate connection,
        // not used by some other connection status judgement.
        connectLock.lock();
        try {
            return channel != null || connectFuture != null;
        } finally {
            connectLock.unlock();
        }
    }

    private Bootstrap configureBootstrapAllocator(Bootstrap bootstrap) {
        ByteBufAllocator testAllocator = TEST_ALLOCATOR.get();
        if (testAllocator != null) {
            return bootstrap.option(ChannelOption.ALLOCATOR, testAllocator);
        } else {
            return bootstrap;
        }
    }

    /**
     * 预留接口，以便自定义配置
     * @param bootstrap
     */
    protected void configureBootstrap(Bootstrap bootstrap) {
    }

    @Override
    void connect(InetSocketAddress addr) throws IOException {
        firstConnect = new CountDownLatch(1);

        Bootstrap bootstrap = new Bootstrap().group(eventLoopGroup)
                                             .channel(NettyUtils.nioOrEpollSocketChannel())
                                             .option(ChannelOption.SO_LINGER, -1)
                                             .option(ChannelOption.TCP_NODELAY, true)
                                             .handler(new ZKClientPipelineFactory(addr.getHostString(), addr.getPort()));
        bootstrap = configureBootstrapAllocator(bootstrap);
        configureBootstrap(bootstrap);
        bootstrap.validate();

        connectLock.lock();
        try {
            connectFuture = bootstrap.connect(addr);
            connectFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    // this lock guarantees that channel won't be assigned after cleanup().
                    boolean connected = false;
                    connectLock.lock();
                    try {
                        if (!channelFuture.isSuccess()) {
                            LOG.warn("future isn't success.", channelFuture.cause());
                            return;
                        } else if (connectFuture == null) {
                            LOG.info("connect attempt cancelled");
                            // If the connect attempt was cancelled but succeeded
                            // anyway, make sure to close the channel, otherwise
                            // we may leak a file descriptor.
                            channelFuture.channel().close();
                            return;
                        }
                        // setup channel, variables, connection, etc.
                        channel = channelFuture.channel();

                        disconnected.set(false);
                        initialized = false;
                        lenBuffer.clear();
                        incomingBuffer = lenBuffer;

                        sendThread.primeConnection();
                        updateNow();
                        updateLastSendAndHeard();

                        if (sendThread.tunnelAuthInProgress()) {
                            waitSasl.drainPermits();
                            needSasl.set(true);
                            sendPrimePacket();
                        } else {
                            needSasl.set(false);
                        }
                        connected = true;
                    } finally {
                        connectFuture = null;
                        connectLock.unlock();
                        if (connected) {
                            LOG.info("channel is connected: {}", channelFuture.channel());
                        }
                        // need to wake on connect success or failure to avoid
                        // timing out ClientCnxn.SendThread which may be
                        // blocked waiting for first connect in doTransport().
                        wakeupCnxn();
                        firstConnect.countDown();
                    }
                }
            });
        } finally {
            connectLock.unlock();
        }
    }

    @Override
    void cleanup() {
        connectLock.lock();
        try {
            if (connectFuture != null) {
                connectFuture.cancel(false);
                connectFuture = null;
            }
            if (channel != null) {
                channel.close();
                channel = null;
            }
        } finally {
            connectLock.unlock();
        }
        Iterator<ClientCnxn.Packet> iter = outgoingQueue.iterator();
        while (iter.hasNext()) {
            ClientCnxn.Packet p = iter.next();
            if (p == WakeupPacket.getInstance()) {
                iter.remove();
            }
        }
    }

    @Override
    void close() {
        eventLoopGroup.shutdownGracefully();
    }

    @Override
    void saslCompleted() {
        needSasl.set(false);
        waitSasl.release();
    }

    @Override
    void connectionPrimed() {
    }

    @Override
    void packetAdded() {
        // NO-OP. Adding a packet will already wake up a netty connection
        // so we don't need to add a dummy packet to the queue to trigger
        // a wake-up.
    }

    @Override
    void onClosing() {
        if (firstConnect != null) {
            firstConnect.countDown();
        }
        wakeupCnxn();
        LOG.info("channel is told closing");
    }

    private void wakeupCnxn() {
        if (needSasl.get()) {
            waitSasl.release();
        }
        if (outgoingQueue != null) {
          outgoingQueue.add(WakeupPacket.getInstance());
        }
    }

    @Override
    void doTransport(
        int waitTimeOut,
        Queue<ClientCnxn.Packet> pendingQueue,
        ClientCnxn cnxn) throws IOException, InterruptedException {
        try {
            if (!firstConnect.await(waitTimeOut, TimeUnit.MILLISECONDS)) {
                return;
            }
            ClientCnxn.Packet head = null;
            if (needSasl.get()) {
                if (!waitSasl.tryAcquire(waitTimeOut, TimeUnit.MILLISECONDS)) {
                    return;
                }
            } else {
                head = outgoingQueue.poll(waitTimeOut, TimeUnit.MILLISECONDS);
            }
            // check if being waken up on closing.
            if (!sendThread.getZkState().isAlive()) {
                // adding back the packet to notify of failure in conLossPacket().
                addBack(head);
                return;
            }
            // channel disconnection happened
            if (disconnected.get()) {
                addBack(head);
                throw new ClientCnxn.EndOfStreamException("channel for sessionid 0x" + Long.toHexString(sessionId) + " is lost");
            }
            if (head != null) {
                doWrite(pendingQueue, head, cnxn);
            }
        } finally {
            updateNow();
        }
    }

    private void addBack(ClientCnxn.Packet head) {
        if (head != null && head != WakeupPacket.getInstance()) {
            outgoingQueue.addFirst(head);
        }
    }

    /**
     * Sends a packet to the remote peer and flushes the channel.
     * @param p packet to send.
     * @return a ChannelFuture that will complete when the write operation
     *         succeeds or fails.
     */
    private ChannelFuture sendPktAndFlush(ClientCnxn.Packet p) throws IOException {
        return sendPkt(p, true);
    }

    /**
     * Sends a packet to the remote peer but does not flush() the channel.
     * @param p packet to send.
     * @return a ChannelFuture that will complete when the write operation
     *         succeeds or fails.
     */
    private ChannelFuture sendPktOnly(ClientCnxn.Packet p) throws IOException {
        return sendPkt(p, false);
    }

    // Use a single listener instance to reduce GC
    private final GenericFutureListener<Future<Void>> onSendPktDoneListener = f -> {
        if (f.isSuccess()) {
            sentCount.getAndIncrement();
        }
    };

    private ChannelFuture sendPkt(ClientCnxn.Packet p, boolean doFlush) throws IOException {
        if (channel == null) {
            throw new IOException("channel has been closed");
        }
        // Assuming the packet will be sent out successfully. Because if it fails,
        // the channel will close and clean up queues.
        p.createBB();
        updateLastSend();
        final ByteBuf writeBuffer = Unpooled.wrappedBuffer(p.bb);
        final ChannelFuture result = doFlush ? channel.writeAndFlush(writeBuffer) : channel.write(writeBuffer);
        result.addListener(onSendPktDoneListener);
        return result;
    }

    private void sendPrimePacket() throws IOException {
        // assuming the first packet is the priming packet.
        sendPktAndFlush(outgoingQueue.remove());
    }

    /**
     * doWrite handles writing the packets from outgoingQueue via network to server.
     */
    private void doWrite(Queue< ClientCnxn.Packet > pendingQueue, ClientCnxn.Packet p, ClientCnxn cnxn) throws IOException {
        updateNow();
        boolean anyPacketsSent = false;
        while (true) {
            if (p != WakeupPacket.getInstance()) {
                if ((p.requestHeader != null)
                    && (p.requestHeader.getType() != ZooDefs.OpCode.ping)
                    && (p.requestHeader.getType() != ZooDefs.OpCode.auth)) {
                    p.requestHeader.setXid(cnxn.getXid());
                    synchronized (pendingQueue) {
                        pendingQueue.add(p);
                    }
                }
                sendPktOnly(p);
                anyPacketsSent = true;
            }
            if (outgoingQueue.isEmpty()) {
                break;
            }
            p = outgoingQueue.remove();
        }
        // TODO: maybe we should flush in the loop above every N packets/bytes?
        // But, how do we determine the right value for N ...
        if (anyPacketsSent) {
            channel.flush();
        }
    }

    @Override
    void sendPacket(ClientCnxn.Packet p) throws IOException {
        sendPktAndFlush(p);
    }

    @Override
    SocketAddress getRemoteSocketAddress() {
        Channel copiedChanRef = channel;
        return (copiedChanRef == null) ? null : copiedChanRef.remoteAddress();
    }

    @Override
    SocketAddress getLocalSocketAddress() {
        Channel copiedChanRef = channel;
        return (copiedChanRef == null) ? null : copiedChanRef.localAddress();
    }

    @Override
    void testableCloseSocket() throws IOException {
        Channel copiedChanRef = channel;
        if (copiedChanRef != null) {
            copiedChanRef.disconnect().awaitUninterruptibly();
        }
    }

    // *************** <END> CientCnxnSocketNetty </END> ******************
    private static class WakeupPacket {

        private static final ClientCnxn.Packet instance = new ClientCnxn.Packet(null, null, null, null, null);

        protected WakeupPacket() {
            // Exists only to defeat instantiation.
        }

        public static ClientCnxn.Packet getInstance() {
            return instance;
        }

    }

    /**
     * ZKClientPipelineFactory is the netty pipeline factory for this netty
     * connection implementation.
     */
    private class ZKClientPipelineFactory extends ChannelInitializer<SocketChannel> {
        private SslContext sslContext = null;
        private final String host;
        private final int port;

        private ZKClientPipelineFactory(String host, int port) {
            this.host = host;
            this.port = port;
        }

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            initProxy(pipeline);
            if (clientConfig.getBoolean(ZKClientConfig.SECURE_CLIENT)) {
                initSSL(pipeline);
            }
            pipeline.addLast("handler", new ZKClientHandler());
        }

        // The synchronized is to prevent the race on shared variable "sslContext".
        // Basically we only need to create it once.
        private synchronized void initSSL(ChannelPipeline pipeline)
            throws X509Exception.KeyManagerException, X509Exception.TrustManagerException, SSLException {
            if (sslContext == null) {
                try (ClientX509Util x509Util = new ClientX509Util()) {
                    sslContext = x509Util.createNettySslContextForClient(clientConfig);
                }
            }
            pipeline.addLast("ssl", sslContext.newHandler(pipeline.channel().alloc(), host, port));
            LOG.info("SSL handler added for channel: {}", pipeline.channel());
        }

        /**
         * ⭕️: 新增的代码，以便启用代理服务器
         */
        protected void initProxy(ChannelPipeline pipeline) {
            String proxyString = clientConfig.getProperty("proxy.socks5");
            if (StringUtils.isBlank(proxyString)) {
                return;
            }
            String[] strArr = proxyString.split(":");
            if (strArr.length != 1 && strArr.length != 2) {
                throw new IllegalArgumentException("zookeeper config `proxy.socks5`=\"" + proxyString + "\" is invalid");
            }
            String proxyHost = strArr[0];
            int proxyPort = 1080;
            if (strArr.length == 2) {
                try {
                    proxyPort = Integer.parseInt(strArr[1]);
                } catch (Exception e) {
                    throw new IllegalArgumentException("zookeeper config `proxy.socks5`=\"" + proxyString + "\" is invalid, cannot parse proxy port");
                }
            }
            LOG.info("config zookeeper to use proxy : proxyHost={}, proxyPort={}", proxyHost, proxyPort);
            pipeline.addLast(new Socks5ProxyHandler(new InetSocketAddress(proxyHost, proxyPort)));
        }

    }

    /**
     * ZKClientHandler is the netty handler that sits in netty upstream last
     * place. It mainly handles read traffic and helps synchronize connection state.
     */
    protected class ZKClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

        AtomicBoolean channelClosed = new AtomicBoolean(false);

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            LOG.info("channel is disconnected: {}", ctx.channel());
            cleanup();
        }

        /**
         * netty handler has encountered problems. We are cleaning it up and tell outside to close
         * the channel/connection.
         */
        private void cleanup() {
            if (!channelClosed.compareAndSet(false, true)) {
                return;
            }
            disconnected.set(true);
            onClosing();
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
            updateNow();
            while (buf.isReadable()) {
                if (incomingBuffer.remaining() > buf.readableBytes()) {
                    int newLimit = incomingBuffer.position() + buf.readableBytes();
                    incomingBuffer.limit(newLimit);
                }
                buf.readBytes(incomingBuffer);
                incomingBuffer.limit(incomingBuffer.capacity());

                if (!incomingBuffer.hasRemaining()) {
                    incomingBuffer.flip();
                    if (incomingBuffer == lenBuffer) {
                        recvCount.getAndIncrement();
                        readLength();
                    } else if (!initialized) {
                        readConnectResult();
                        lenBuffer.clear();
                        incomingBuffer = lenBuffer;
                        initialized = true;
                        updateLastHeard();
                    } else {
                        sendThread.readResponse(incomingBuffer);
                        lenBuffer.clear();
                        incomingBuffer = lenBuffer;
                        updateLastHeard();
                    }
                }
            }
            wakeupCnxn();
            // Note: SimpleChannelInboundHandler releases the ByteBuf for us
            // so we don't need to do it.
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            LOG.error("Unexpected throwable", cause);
            cleanup();
        }

    }

    /**
     * Sets the test ByteBufAllocator. This allocator will be used by all
     * future instances of this class.
     * It is not recommended to use this method outside of testing.
     * @param allocator the ByteBufAllocator to use for all netty buffer
     *                  allocations.
     */
    static void setTestAllocator(ByteBufAllocator allocator) {
        TEST_ALLOCATOR.set(allocator);
    }

    /**
     * Clears the test ByteBufAllocator. The default allocator will be used
     * by all future instances of this class.
     * It is not recommended to use this method outside of testing.
     */
    static void clearTestAllocator() {
        TEST_ALLOCATOR.set(null);
    }


}
