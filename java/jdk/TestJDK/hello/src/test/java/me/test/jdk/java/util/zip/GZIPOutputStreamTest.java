package me.test.jdk.java.util.zip;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author dangqian.zll
 * @date 2023/12/18
 */
public class GZIPOutputStreamTest {

    /**
     * at java.base@11.0.18.17-AJDK/jdk.internal.ref.PhantomCleanable.insert(PhantomCleanable.java:87)
     * -  locked jdk.internal.ref.CleanerImpl$PhantomCleanableRef@83ef4eb
     * at java.base@11.0.18.17-AJDK/jdk.internal.ref.PhantomCleanable.<init>(PhantomCleanable.java:68)
     * at java.base@11.0.18.17-AJDK/jdk.internal.ref.CleanerImpl$PhantomCleanableRef.<init>(CleanerImpl.java:172)
     * at java.base@11.0.18.17-AJDK/java.lang.ref.Cleaner.register(Cleaner.java:220)
     * at java.base@11.0.18.17-AJDK/java.util.zip.Deflater$DeflaterZStreamRef.<init>(Deflater.java:970)
     * at java.base@11.0.18.17-AJDK/java.util.zip.Deflater$DeflaterZStreamRef.get(Deflater.java:1006)
     * at java.base@11.0.18.17-AJDK/java.util.zip.Deflater.<init>(Deflater.java:207)
     * at java.base@11.0.18.17-AJDK/java.util.zip.GZIPOutputStream.<init>(GZIPOutputStream.java:91)
     * at java.base@11.0.18.17-AJDK/java.util.zip.GZIPOutputStream.<init>(GZIPOutputStream.java:110)
     *
     * @throws IOException
     * @see GZIPOutputStream
     * @see jdk.internal.ref.PhantomCleanable#insert
     * @see jdk.internal.ref.CleanerFactory
     * @see Deflater.DeflaterZStreamRef#DeflaterZStreamRef(Deflater, long)
     */
    @Test
    public void test() throws IOException {
        GZIPOutputStream out = new GZIPOutputStream(new ByteArrayOutputStream());
        // jdk.internal.ref.CleanerFactory.commonCleaner.impl.phantomCleanableList

        new GZIPOutputStream(new ByteArrayOutputStream());
        new GZIPOutputStream(new ByteArrayOutputStream());
    }

    protected int get() {
        return 0;
    }

    @Test
    public void test01() {
        // "hello world" => "H4sIAAAAAAAA/8tIzcnJVyjPL8pJAQCFEUoNCwAAAA=="
        // "hello zhang3" => "H4sIAAAAAAAA/8tIzcnJV6jKSMxLNwYANwO2lAwAAAA="
        // "hello li4" => "H4sIAAAAAAAA/8tIzcnJV8jJNAEAwCmPxQkAAAA="
        System.out.println(getGzipBase64FromStr("hello world"));
        System.out.println(getGzipBase64FromStr("hello zhang3"));
        System.out.println(getGzipBase64FromStr("hello li4"));

        String str = "hello world";
        Assertions.assertEquals(getGzipBase64FromStr(str), getGzipBase64FromStr2(str));
        str = "hello zhang3";
        Assertions.assertEquals(getGzipBase64FromStr(str), getGzipBase64FromStr2(str));
        str = "hello li4";
        Assertions.assertEquals(getGzipBase64FromStr(str), getGzipBase64FromStr2(str));

    }

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


    // ==============

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
            String s =  new String(byteArrOut.toByteArray(), StandardCharsets.UTF_8);
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
