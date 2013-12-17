
package me.test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import nl.captcha.text.producer.TextProducer;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An alternative to SimpleCaptcha's {@link nl.captcha.text.producer.ChineseTextProducer ChineseTextProducer} which may
 * using rarely used characters 。
 * This class will only generate text using level of commonly used Chinese characters.
 *<p>
 * Reference: <a href="http://en.wikipedia.org/wiki/GB_2312">GB2312</a>,
 * <a href="http://en.wikipedia.org/wiki/GBK">GBK</a>,
 * <a href="http://en.wikipedia.org/wiki/ISO_2022">ISO2022</a>
 * </p>
 */
public class MyChineseTextProducer implements TextProducer {

    private CharsetDecoder gbkCharsetDecoder = Charset.forName("GBK").newDecoder();

    private SecureRandom random;

    private final int count;
    private StringBuilder strBuf;
    private ByteBuffer byteBuf = ByteBuffer.allocate(2);
    private CharBuffer charBuf = CharBuffer.allocate(2);
    private Logger logger = LoggerFactory.getLogger(MyChineseTextProducer.class);

    public MyChineseTextProducer() {

        this(6);
    }

    public MyChineseTextProducer(int count) {

        this.count = count;
        this.strBuf = new StringBuilder(count);
        try {
            this.random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public synchronized String getText() {

        strBuf.setLength(0);
        for (int i = 0; i < this.count; i++) {
            strBuf.append(getRandomClass1ChineseChar());
        }

        return strBuf.toString();
    }

    /**
     * get a random Chinese character in first plane for Chinese characters.
     *
     * @return a Chinese character
     */
    private char getRandomClass1ChineseChar() {

        byteBuf.clear();
        charBuf.clear();
        gbkCharsetDecoder.reset();

        // 区：16~55
        byte zone = (byte) (this.random.nextInt(55 - 16 + 1) + 16);
        // 位：01~94
        byte position = (byte) (this.random.nextInt(94) + 1);
        byteBuf.put((byte) (zone + 0xA0));
        byteBuf.put((byte) (position + 0xA0));
        byteBuf.rewind();
        charBuf.rewind();

        gbkCharsetDecoder.decode(byteBuf, charBuf, true);
        char chineseChar = charBuf.get(0);
        if (logger.isDebugEnabled()) {
            logger.debug("using chinese character : [{},{}] = 0x{} '{}'",
                    zone, position, Hex.encodeHexString(byteBuf.array()).toUpperCase(), chineseChar);
        }
        return chineseChar;

    }

    public static void main(String[] args) {

        TextProducer t = new MyChineseTextProducer();
        for (int i = 0; i < 10; i++) {
            System.out.println(t.getText());
        }
    }
}
