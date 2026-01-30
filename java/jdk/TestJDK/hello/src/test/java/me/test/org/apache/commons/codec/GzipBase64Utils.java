package me.test.org.apache.commons.codec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.annotation.Nonnull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.io.IOUtils;

/**
 * @author dangqian.zll
 * @date 2024/7/2
 */
@Slf4j
public class GzipBase64Utils {
    private static final int BUF_SIZE = 4 * 1024;

    @Nonnull
    public static String getGzipBase64FromBytes(@Nonnull byte[] bytes) {
        try {
            InputStream strIn = new ByteArrayInputStream(bytes);
            ByteArrayOutputStream byteArrOut = new ByteArrayOutputStream(2 * 1024);
            try (
                    OutputStream base64Out = new Base64OutputStream(byteArrOut);
                    GZIPOutputStream gzipOut = new GZIPOutputStream(base64Out);
            ) {
                IOUtils.copy(strIn, gzipOut);
            }
            // 注意：该语句不能放在 try 里面
            return new String(byteArrOut.toByteArray(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("failed to encode data to gzipBase64 str", e);
        }
    }

    @Nonnull
    public static String getGzipBase64FromStr(@Nonnull String str) {
        return getGzipBase64FromBytes(str.getBytes(StandardCharsets.UTF_8));
    }

    @Nonnull
    public static byte[] getBytesFromGzipBase64(@Nonnull String str) {
        try (
                InputStream strIn = IOUtils.toInputStream(str, StandardCharsets.UTF_8);
                Base64InputStream base64In = new Base64InputStream(strIn);
                GZIPInputStream gzipIn = new GZIPInputStream(base64In);
        ) {
            return IOUtils.toByteArray(gzipIn);
        } catch (IOException e) {
            throw new RuntimeException("failed to decode data from gzipBase64 str", e);
        }
    }

    @Nonnull
    public static String getStrFromGzipBase64(@Nonnull String str) {
        byte[] bytes = getBytesFromGzipBase64(str);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * 只使用JDK的类，简单实现
     */
    @SneakyThrows
    public static String getGzipBase64FromBytes2(byte[] bytes) {
        ByteArrayOutputStream byteArrOut = new ByteArrayOutputStream(2 * 1024);
        GZIPOutputStream gzipOut = new GZIPOutputStream(byteArrOut);
        gzipOut.write(bytes);
        gzipOut.close();
        byte[] gzipBytes = byteArrOut.toByteArray();
        return Base64.getEncoder().encodeToString(gzipBytes);
    }

    /**
     * 只使用JDK的类，简单实现
     */
    @SneakyThrows
    public static byte[] getBytesFromGzipBase642(String str) {
        byte[] gzipBytes = Base64.getDecoder().decode(str);
        ByteArrayInputStream byteArrIn = new ByteArrayInputStream(gzipBytes);
        GZIPInputStream gzipIn = new GZIPInputStream(byteArrIn);
        byte[] plainBytes = gzipIn.readAllBytes();
        gzipIn.close();
        return plainBytes;
    }
}
