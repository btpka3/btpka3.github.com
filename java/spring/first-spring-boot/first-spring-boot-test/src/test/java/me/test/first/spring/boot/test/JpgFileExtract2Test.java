package me.test.first.spring.boot.test;

import com.drew.imaging.jpeg.JpegSegmentReader;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * @author dangqian.zll
 * @date 2021/4/29
 * @see <a href="https://github.com/drewnoakes/metadata-extractor">github : drewnoakes/metadata-extractor</a>
 * @see <a href="https://drewnoakes.com/code/exif/">drewnoakes/metadata-extractor</a>
 * @see <a href="https://github.com/junrar/junrar">junrar</a>
 * @see <a href="https://www.online-tech-tips.com/computer-tips/hide-file-in-picture/">How To Hide Files In a JPG Picture</a>
 * @see <a href="https://blog.csdn.net/lidawei201/article/details/45849897">用图片隐藏信息的技术实现</a>
 * @see <a href="http://www.lingala.net/zip4j.html">Zip4j</a>
 * @see <a href="https://pzemtsov.github.io/2019/04/13/searching-for-better-indexof.html">Experiments in program optimisation</a>
 * @see <a href="https://stackoverflow.com/questions/4585527/detect-eof-for-jpg-images">Detect Eof for JPG images</a>
 * @see <a href="https://en.wikipedia.org/wiki/JPEG">JPEG</a>
 * @see JpegSegmentReader#readSegments
 */
public class JpgFileExtract2Test {

    @SneakyThrows
    @Test
    public void test() {


        URL defaultImage = JpgFileExtract2Test.class.getResource("a.jpg");
        File imageFile = new File(defaultImage.toURI());

        RandomAccessFile randomAccessFile = new RandomAccessFile(imageFile, "r");

        byte[] bytes = new byte[2];
        randomAccessFile.read(bytes, 0, 2);
        // segment : SOI
        Assertions.assertEquals(0xFF, randomAccessFile.read());
        Assertions.assertEquals(0xD8, randomAccessFile.read());

    }

    @Test
    public void testPrintSegmentList() {
        print(Arrays.asList(
                JpgSegment.builder()
                        .segmentType((byte) 0xD8)
                        .payloadLen(0)
                        .startPos(0)
                        .endPos(3)
                        .build(),
                JpgSegment.builder()
                        .segmentType((byte) 0xC0)
                        .payloadLen(128)
                        .startPos(100)
                        .endPos(228)
                        .build(),
                JpgSegment.builder()
                        .segmentType((byte) 0xD9)
                        .payloadLen(0)
                        .startPos(200)
                        .endPos(203)
                        .build()
        ));
    }

    public void print(List<JpgSegment> list) {
        System.out.printf("%4s | %13s | %13s | %13s |%n", "type", "playload len", "start offset", "end offset");
        for (JpgSegment jpgSegment : list) {
            System.out.printf("0x%02X | %13d | %13d | %13d |%n",
                    jpgSegment.getSegmentType(),
                    jpgSegment.getPayloadLen(),
                    jpgSegment.getStartPos(),
                    jpgSegment.getEndPos()
            );
        }
    }


    @Data
    @Builder
    public static class JpgSegment {

        /**
         *
         */
        private byte segmentType;

        /**
         * playload 中长度
         */
        @Builder.Default
        private int payloadLen = -1;


        /**
         * 在 JPEG 文件中的 开始的 position (含）, 该位置的字节应该是 0xFF。
         */
        private long startPos;
        /**
         * 在 JPEG 文件中的 结束的 position (不含）
         */
        private long endPos;


    }
}

