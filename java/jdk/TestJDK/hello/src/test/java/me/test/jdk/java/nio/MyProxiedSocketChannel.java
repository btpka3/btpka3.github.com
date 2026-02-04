package me.test.jdk.java.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import lombok.SneakyThrows;

/**
 *
 * @author dangqian.zll
 * @date 2025/10/14
 */
public class MyProxiedSocketChannel extends SocketChannel {
    /**
     * Set of supported options.
     */
    private static final Set<SocketOption<?>> SUPPORTED_OPTIONS;

    static {
        final HashSet<SocketOption<?>> set = new HashSet<SocketOption<?>>();
        set.add(StandardSocketOptions.SO_KEEPALIVE);
        set.add(StandardSocketOptions.SO_RCVBUF);
        set.add(StandardSocketOptions.SO_SNDBUF);
        set.add(StandardSocketOptions.SO_REUSEADDR);
        set.add(StandardSocketOptions.TCP_NODELAY);
        set.add(StandardSocketOptions.SO_LINGER);
        SUPPORTED_OPTIONS = Collections.unmodifiableSet(set);
    }

    /**
     * Constant for setting socket timeout to <em>infinite</em>.
     */
    private static final int TIMEOUT_INFINITE = 0;

    /**
     * Return code for EOF (end-of-file).
     */
    private static final int EOF = -1;

    /**
     * The underlying "classic" socket which does have proxy support.
     */
    private final Socket socket;

    @SneakyThrows
    public MyProxiedSocketChannel(final Proxy proxy, SelectorProvider provider) {
        super(provider);
        if (proxy == null) {
            this.socket = new Socket(Proxy.NO_PROXY);
        } else {
            this.socket = new Socket(proxy);
        }
        // set timeout to 0 (infinite) since socketchannel generally does not
        // time out (no SocketOption available either)
        this.socket.setSoTimeout(TIMEOUT_INFINITE);
    }

    /**
     * Create a proxied socket channel with provided proxy.
     *
     * @param proxy the required proxy configuration (null == Proxy.NO_PROXY)
     * @throws IOException
     */
    public MyProxiedSocketChannel(final Proxy proxy) {
        this(proxy, SelectorProvider.provider());
    }

    /**
     * Initializes a new instance of this class.
     *
     * @param provider The provider that created this channel
     */
    protected MyProxiedSocketChannel(SelectorProvider provider) {
        this(Proxy.NO_PROXY, provider);
    }

    @Override
    public SocketAddress getLocalAddress() throws IOException {
        return this.socket.getLocalSocketAddress();
    }

    @Override
    public SocketChannel bind(final SocketAddress local) throws IOException {
        this.socket.bind(local);
        return this;
    }

    @Override
    public Set<SocketOption<?>> supportedOptions() {
        return SUPPORTED_OPTIONS;
    }

    @Override
    public <T> T getOption(final SocketOption<T> option) throws IOException {
        if (StandardSocketOptions.SO_KEEPALIVE.equals(option)) {
            return (T) StandardSocketOptions.SO_KEEPALIVE.type().cast(this.socket.getKeepAlive());
        } else if (StandardSocketOptions.SO_RCVBUF.equals(option)) {
            return (T) StandardSocketOptions.SO_RCVBUF.type().cast(this.socket.getReceiveBufferSize());
        } else if (StandardSocketOptions.SO_SNDBUF.equals(option)) {
            return (T) StandardSocketOptions.SO_SNDBUF.type().cast(this.socket.getSendBufferSize());
        } else if (StandardSocketOptions.SO_REUSEADDR.equals(option)) {
            return (T) StandardSocketOptions.SO_REUSEADDR.type().cast(this.socket.getReuseAddress());
        } else if (StandardSocketOptions.TCP_NODELAY.equals(option)) {
            return (T) StandardSocketOptions.TCP_NODELAY.type().cast(this.socket.getTcpNoDelay());
        } else if (StandardSocketOptions.SO_LINGER.equals(option)) {
            return (T) StandardSocketOptions.SO_LINGER.type().cast(this.socket.getSoLinger());
        }
        throw new IllegalArgumentException("Unsupported option specified.");
    }

    @Override
    public <T> SocketChannel setOption(final SocketOption<T> option, final T value) throws IOException {
        if (StandardSocketOptions.SO_KEEPALIVE.equals(option)) {
            final Class<Boolean> keepaliveType = StandardSocketOptions.SO_KEEPALIVE.type();
            this.socket.setKeepAlive(keepaliveType.cast(value));
        } else if (StandardSocketOptions.SO_RCVBUF.equals(option)) {
            final Class<Integer> rcvbufType = StandardSocketOptions.SO_RCVBUF.type();
            this.socket.setReceiveBufferSize(rcvbufType.cast(value));
        } else if (StandardSocketOptions.SO_SNDBUF.equals(option)) {
            final Class<Integer> sndbufType = StandardSocketOptions.SO_SNDBUF.type();
            this.socket.setSendBufferSize(sndbufType.cast(value));
        } else if (StandardSocketOptions.SO_REUSEADDR.equals(option)) {
            final Class<Boolean> reuseType = StandardSocketOptions.SO_REUSEADDR.type();
            this.socket.setReuseAddress(reuseType.cast(value));
        } else if (StandardSocketOptions.TCP_NODELAY.equals(option)) {
            final Class<Boolean> nodelayType = StandardSocketOptions.TCP_NODELAY.type();
            this.socket.setTcpNoDelay(nodelayType.cast(value));
        } else if (StandardSocketOptions.SO_LINGER.equals(option)) {
            final Class<Integer> lingerType = StandardSocketOptions.SO_LINGER.type();
            final Integer lingerValue = lingerType.cast(value);
            final boolean enabled = lingerValue >= 0;
            this.socket.setSoLinger(enabled, lingerValue);
        }
        throw new IllegalArgumentException("Unsupported option specified.");
    }

    @Override
    public SocketChannel shutdownInput() throws IOException {
        // FIXME synchronize on InputStream before shutting down?
        this.socket.shutdownInput();
        return this;
    }

    @Override
    public SocketChannel shutdownOutput() throws IOException {
        // FIXME synchronize on OutputStream before shutting down?
        this.socket.shutdownOutput();
        return this;
    }

    @Override
    public Socket socket() {
        return this.socket;
    }

    @Override
    public boolean isConnected() {
        return this.socket.isConnected();
    }

    @Override
    public boolean isConnectionPending() {
        return false;
    }

    @Override
    public boolean connect(final SocketAddress remote) throws IOException {
        this.socket.connect(remote);
        return true;
    }

    @Override
    public boolean finishConnect() throws IOException {
        return this.socket.isConnected();
    }

    @Override
    public SocketAddress getRemoteAddress() throws IOException {
        return this.socket.getRemoteSocketAddress();
    }

    @Override
    public int read(final ByteBuffer dst) throws IOException {
        final InputStream input = this.socket.getInputStream();
        synchronized (input) {
            return readBlocking(input, dst);
        }
    }

    /**
     * Read from provided input stream in blocking fashion.
     * <p>
     * NOTE: assumes that calling thread is already <em>synchronized</em> on
     * input stream!
     *
     * @param input the SYNCHRONIZED input stream
     * @param dst   destination buffer
     * @return returns number of bytes read
     * @throws IOException in case of IOException
     */
    private int readBlocking(final InputStream input, final ByteBuffer dst) throws IOException {
        final byte[] buffer = new byte[dst.remaining()];
        final int size = input.read(buffer);
        if (size > 0) {
            dst.put(buffer, 0, size);
        }
        return size;
    }

    @Override
    public long read(final ByteBuffer[] dsts, final int offset, final int length) throws IOException {
        final InputStream input = this.socket.getInputStream();
        synchronized (input) {
            return readBlocking(input, dsts, offset, length);
        }
    }

    /**
     * Read from provided input stream in blocking fashion.
     * <p>
     * NOTE: assumes that calling thread is already <em>synchronized</em> on
     * input stream!
     *
     * @param input  the SYNCHRONIZED input stream
     * @param dsts   the array of destination buffers
     * @param offset the offset for first available buffer
     * @param length the number of buffers to use
     * @return returns number of bytes read
     * @throws IOException
     */
    private long readBlocking(final InputStream input, final ByteBuffer[] dsts, final int offset, final int length)
            throws IOException {
        int total = 0;
        for (int i = offset; i < offset + length; i++) {
            final int size = readBlocking(input, dsts[i]);
            if (size == EOF) {
                if (total == 0) {
                    // Very first response is EOF. Signal EOF to reader.
                    return EOF;
                } else {
                    // EOF is not very first data read, so break and return
                    // whatever we have read so far.
                    break;
                }
            }
            total += size;
        }
        return total;
    }

    @Override
    public int write(final ByteBuffer src) throws IOException {
        final OutputStream output = this.socket.getOutputStream();
        synchronized (output) {
            return writeBlocking(output, src);
        }
    }

    /**
     * Write to provided output stream in blocking fashion.
     * <p>
     * NOTE: assumes that calling thread is already synchronized on output
     * stream!
     *
     * @param output the SYNCHRONIZED output stream
     * @param src    the source
     * @return returns number of bytes written
     * @throws IOException in case of exceptions
     */
    private int writeBlocking(final OutputStream output, final ByteBuffer src) throws IOException {
        final byte[] buffer = new byte[src.remaining()];
        src.get(buffer);
        output.write(buffer);
        return buffer.length;
    }

    @Override
    public long write(final ByteBuffer[] srcs, final int offset, final int length) throws IOException {
        final OutputStream output = this.socket.getOutputStream();
        synchronized (output) {
            return writeBlocking(output, srcs, offset, length);
        }
    }

    /**
     * Write to provided output stream in blocking fashion.
     * <p>
     * NOTE: assumes that calling thread is already <em>synchronized</em> on
     * output stream!
     *
     * @param output the SYNCHRONIZED output stream
     * @param srcs   the sources
     * @param offset the offset for the initial src buffer
     * @param length the number of src buffers available
     * @return returns number of bytes written
     * @throws IOException in case of exceptions
     */
    private long writeBlocking(final OutputStream output, final ByteBuffer[] srcs, final int offset, final int length)
            throws IOException {
        int total = 0;
        for (int i = offset; i < offset + length; i++) {
            total += writeBlocking(output, srcs[i]);
        }
        return total;
    }

    @Override
    protected void implCloseSelectableChannel() throws IOException {
        this.socket.close();
    }

    @Override
    protected void implConfigureBlocking(final boolean block) throws IOException {
        if (block) {
            return;
        }
        throw new UnsupportedOperationException("Non-blocking mode is not supported yet.");
    }
}
