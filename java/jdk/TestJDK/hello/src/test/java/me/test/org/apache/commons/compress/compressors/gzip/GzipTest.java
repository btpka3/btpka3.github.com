package me.test.org.apache.commons.compress.compressors.gzip;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2020-04-17
 */
public class GzipTest {

    @Test
    public void testGzipBase64() throws IOException {
        String str = "helloddd";
        System.out.println("str    = " + str);
        // + Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8))

        // cat a.txt |gzip| base64
        // 期待： H4sIALhUmV4AA8tIzcnJBwCGphA2BQAAAA==
        //       H4sIAAAAAAAAAMpIzcnJBwAAAP//
        String base64Str = toGzipBase64(str);
        System.out.println("base64 = " + base64Str);

        String newStr = fromGzipBase64(base64Str);
        System.out.println("newStr = " + newStr);

    }

    @Test
    public void testCompressionRatio() throws IOException {
//        String f= "/Users/zll/data0/work/git-repo/github/btpka3/btpka3.github.com/java/jdk/TestJDK/hello/src/test/java/me/test/org/apache/commons/compress/compressors/gzip/GzipTest.java";
        String f = "/Users/zll/Downloads/b.txt";
        String txt = IOUtils.toString(new FileInputStream(f), StandardCharsets.UTF_8);
        System.out.println("raw text size : " + txt.length());
        String base64Str = toGzipBase64(txt);
        System.out.println("new text size : " + base64Str.length() + ", percent=" + (1.0 * base64Str.length() / txt.length()));

    }

    @Test
    public void x() throws IOException {
        InputStream strIn = IOUtils.toInputStream("hello", StandardCharsets.UTF_8);
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        IOUtils.copy(strIn, baos);
        System.out.println(new String(baos.toByteArray(), StandardCharsets.UTF_8));
    }


    public static String encodeBase64ZippedString(String in) throws IOException {
        ByteArrayOutputStream byteArrOut = new ByteArrayOutputStream(1024);
        try (OutputStream base64Out = new Base64OutputStream(byteArrOut);
             OutputStream gzipOut = new GZIPOutputStream(base64Out)
        ) {
            gzipOut.write(in.getBytes(StandardCharsets.UTF_8));
        }
        return byteArrOut.toString();
    }

    protected String toGzipBase64(String str) throws IOException {
        InputStream strIn = IOUtils.toInputStream(str, StandardCharsets.UTF_8);

        ByteArrayOutputStream byteArrOut = new ByteArrayOutputStream(2 * 1024);
        try (
                OutputStream base64Out = new Base64OutputStream(byteArrOut);
                GZIPOutputStream gzipOut = new GZIPOutputStream(base64Out);
        ) {
            IOUtils.copy(strIn, gzipOut);
        }

        // 注意：该语句不能放在 try 里面
        return new String(byteArrOut.toByteArray(), StandardCharsets.UTF_8);
    }

    protected String fromGzipBase64(String str) throws IOException {
        try (
                InputStream strIn = IOUtils.toInputStream(str, StandardCharsets.UTF_8);
                Base64InputStream base64In = new Base64InputStream(strIn);
                GZIPInputStream gzipIn = new GZIPInputStream(base64In);
        ) {
            return IOUtils.toString(gzipIn, StandardCharsets.UTF_8);
        }
    }
}
