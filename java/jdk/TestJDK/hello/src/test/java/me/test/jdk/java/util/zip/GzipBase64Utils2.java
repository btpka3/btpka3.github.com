package me.test.jdk.java.util.zip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;
import javax.annotation.Nonnull;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.io.IOUtils;

/**
 * @author dangqian.zll
 * @date 2025/7/8
 */
public class GzipBase64Utils2 {
    @Nonnull
    public static String getGzipBase64FromBytes2(@Nonnull byte[] bytes) {
        try {
            InputStream strIn = new ByteArrayInputStream(bytes);

            //ByteArrayOutputStream byteArrOut = new ByteArrayOutputStream(2 * 1024);
            //try
            //(
//                    OutputStream base64Out = new Base64OutputStream(byteArrOut);
//                    GZIPOutputStream gzipOut = new GZIPOutputStream(base64Out);
            //)
            //{
            IOUtils.copy(strIn, gzipOut);

            //}

            // 注意：该语句不能放在 try 里面
            String s = new String(byteArrOut.toByteArray(), StandardCharsets.UTF_8);
            //byteArrOut.reset();
            return s;
        } catch (IOException e) {
            throw new RuntimeException("failed to encode data to gzipBase64 str", e);
        }
    }

    @Nonnull
    public static String getGzipBase64FromStr2(@Nonnull String str) {
        return getGzipBase64FromBytes2(str.getBytes(StandardCharsets.UTF_8));
    }

    //ByteArrayInputStream bais = new ByteArrayInputStream( );
    static ByteArrayOutputStream byteArrOut = new ByteArrayOutputStream(2 * 1024);
    static OutputStream base64Out = new Base64OutputStream(byteArrOut);
    static GZIPOutputStream gzipOut;

    static {
        try {
            gzipOut = new GZIPOutputStream(base64Out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
