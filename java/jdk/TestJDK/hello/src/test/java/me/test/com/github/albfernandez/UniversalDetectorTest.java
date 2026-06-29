package me.test.com.github.albfernandez;

import lombok.SneakyThrows;
import org.mozilla.universalchardet.UniversalDetector;

/**
 *
 * @author dangqian.zll
 * @date 2026/6/29
 */
public class UniversalDetectorTest {

    @SneakyThrows
    public void x() {
        byte[] buf = new byte[4096];
        java.io.InputStream fis = java.nio.file.Files.newInputStream(java.nio.file.Paths.get("test.txt"));

        // (1)
        UniversalDetector detector = new UniversalDetector();

        // (2)
        int nread;
        while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
            detector.handleData(buf, 0, nread);
        }
        // (3)
        detector.dataEnd();

        // (4)
        String encoding = detector.getDetectedCharset();
        if (encoding != null) {
            System.out.println("Detected encoding = " + encoding);
        } else {
            System.out.println("No encoding detected.");
        }

        // (5)
        detector.reset();
    }
}
