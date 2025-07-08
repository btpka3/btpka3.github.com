package me.test.jdk.java.util.zip;

import javax.annotation.Nonnull;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author dangqian.zll
 * @date 2025/7/8
 */
public class GzipBase64Utils {
    @Nonnull
    public static String getGzipBase64FromStr(@Nonnull String str) {
        return getGzipBase64FromBytes(str.getBytes(StandardCharsets.UTF_8));
    }

    @Nonnull
    public static String getGzipBase64FromBytes(@Nonnull byte[] bytes) {
        try {
            ByteArrayOutputStream byteArrOut = new ByteArrayOutputStream(2 * 1024);
            try (
                    GZIPOutputStream gzipOut = new GZIPOutputStream(byteArrOut);
            ) {
                gzipOut.write(bytes);
            }
            byte[] gzipBytes = byteArrOut.toByteArray();
            return Base64.getEncoder().encodeToString(gzipBytes);
        } catch (IOException e) {
            throw new RuntimeException("failed to encode data to gzipBase64 str", e);
        }
    }


    @Nonnull
    public static String getStrFromGzipBase64(@Nonnull String str) {
        byte[] bytes = getBytesFromGzipBase64(str);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static byte[] getBytesFromGzipBase64(@Nonnull String str) {
        byte[] base64Bytes = str.getBytes(StandardCharsets.UTF_8);
        byte[] gzipBytes = Base64.getDecoder().decode(base64Bytes);
        try (
                InputStream gipIn = new ByteArrayInputStream(gzipBytes);
                GZIPInputStream gzipIn = new GZIPInputStream(gipIn);
        ) {
            return gzipIn.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("failed to decode data from gzipBase64 str", e);
        }
    }
}
