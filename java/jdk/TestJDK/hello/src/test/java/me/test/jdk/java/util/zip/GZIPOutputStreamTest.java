package me.test.jdk.java.util.zip;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
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

        String str = "hello world1234567890AliAliAlibaba111222333444555";
        String expectedBase64 = "H4sIAAAAAAAA/8tIzcnJVyjPL8pJMTQyNjE1M7ewNHDMyYSgpMSkRENDQyMjI2NjYxMTE1NTUwDoEDY/MQAAAA==";
        String resultBase64 = GzipBase64Utils.getGzipBase64FromStr(str);
        System.out.println(resultBase64);
        Assertions.assertEquals(expectedBase64, resultBase64);
        String resultStr = GzipBase64Utils.getStrFromGzipBase64(resultBase64);
        Assertions.assertEquals(str, resultStr);
    }

}
