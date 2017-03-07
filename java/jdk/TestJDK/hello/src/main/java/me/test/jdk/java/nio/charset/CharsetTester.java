package me.test.jdk.java.nio.charset;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;

public class CharsetTester {

    public static void main(String[] args) throws UnsupportedEncodingException, CharacterCodingException {

        CharBuffer charBuf = CharBuffer.allocate(100);

        ByteBuffer byteBuf = ByteBuffer.allocate(100);
        // byteBuf.put("I我".getBytes("UTF-8"));
        // 'I'
        byteBuf.put((byte) 73);
        // '我' UTF-8(-26, -120, -111,) first byte
        byteBuf.put((byte) -26);
        byteBuf.put((byte) -120);
        byteBuf.put((byte) -111);
        // '我' UTF-8(-26, -120, -111,) first byte
        byteBuf.put((byte) -26); // malformed
        // byteBuf.put((byte) -120);
        // 'I'
        // byteBuf.put((byte) 73);
        byteBuf.put((byte) 73);
        byteBuf.put((byte) 73);
        byteBuf.flip();

        CharsetDecoder cd = StandardCharsets.UTF_8
                .newDecoder()
                // .onMalformedInput(CodingErrorAction.REPLACE)
                .onUnmappableCharacter(CodingErrorAction.REPLACE);

        cd.reset();

        while (true) {
            CoderResult cr = cd.decode(byteBuf, charBuf, false);
            if (!cr.isError()) {
                break;
            }
            System.out.println(cr.isError() + "," + cr);
            if (cr.isMalformed()) {
                byteBuf.position(byteBuf.position() + cr.length());
                charBuf.put(cd.replacement());
            }
        }

        System.out.println(byteBuf + ", "
                + "CharBuf[pos=" + charBuf.position() + " lim=" + charBuf.limit()
                + " cap=" + charBuf.capacity() + "]");
        charBuf.flip();
        System.out.println(charBuf);
    }
}
