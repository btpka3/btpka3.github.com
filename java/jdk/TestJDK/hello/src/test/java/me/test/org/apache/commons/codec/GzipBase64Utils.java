package me.test.org.apache.commons.codec;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.StopWatch;

import javax.annotation.Nonnull;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import java.io.InputStream;

/**
 * @author dangqian.zll
 * @date 2024/7/2
 */
@Slf4j
public class GzipBase64Utils {
    private static final int BUF_SIZE =  4*1024;

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
}
