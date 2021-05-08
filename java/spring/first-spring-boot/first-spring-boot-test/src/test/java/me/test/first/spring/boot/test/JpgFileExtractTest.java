package me.test.first.spring.boot.test;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.imaging.jpeg.JpegSegmentReader;
import com.drew.lang.SequentialReader;
import com.drew.lang.StreamReader;
import com.drew.lang.annotations.NotNull;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.github.junrar.Junrar;
import lombok.SneakyThrows;
import net.lingala.zip4j.ZipFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;

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
 */
public class JpgFileExtractTest {

    /*
        cat 1.jpg 2.zip > 3.jpg
     */

    @SneakyThrows
    @Test
    public void getFileFromJpg01() {
        InputStream inputStream = JpgFileExtractTest.class.getResourceAsStream("a.jpg");
        Metadata metadata = ImageMetadataReader.readMetadata(inputStream);
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                System.out.println(tag);
            }
        }
    }

    /**
     * JPEG 文件 会 以 [FF D8] 开头，以 [FF D9] 结束
     */
    @SneakyThrows
    @Test
    public void splitJpgFile() {

        InputStream inputStream = JpgFileExtractTest.class.getResourceAsStream("a.jpg");
        byte[] bytes = IOUtils.toByteArray(inputStream);

        int idx = 0;
        if (bytes.length < 2) {
            for (int i = 0; i < bytes.length - 2; i++) {

                if (bytes[i] == (byte) 0xFF) {
                    System.out.println("0xFF idx  =" + i);
                }
                if (bytes[i] == (byte) 0xFF && bytes[i + 1] == (byte) 0xD9) {
                    idx = i;
                    break;
                }
            }
        }
        if (idx == 0) {
            System.out.printf("Not found, return.");
            return;
        }

        System.out.println("idx = " + idx);
        {
            byte[] jpgBytes = ArrayUtils.subarray(bytes, 0, idx + 2);
            IOUtils.write(jpgBytes, new FileOutputStream("/tmp/a.jpg"));
        }

        {
            byte[] zipFileBytes = ArrayUtils.subarray(bytes, idx + 2, bytes.length - 1);
            IOUtils.write(zipFileBytes, new FileOutputStream("/tmp/a.zip"));
        }
        System.out.println("done.");
    }

    /**
     * @see JpegSegmentReader#readSegments
     */
    @SneakyThrows
    @Test
    public void x() {
        InputStream inputStream = JpgFileExtractTest.class.getResourceAsStream("a.jpg");
//        File file = new File("/tmp/1.jpg");
//        FileUtils.copyInputStreamToFile(inputStream, file);

        StreamReader sr = new StreamReader(inputStream);
        int i = readSegments(sr);
        System.out.println("i = " + i);
        System.out.println("getPosition = " + sr.getPosition());
//        JpegSegmentData segmentTypeBytes = JpegSegmentReader.readSegments(file, null);

    }

    private static final byte SEGMENT_IDENTIFIER = (byte) 0xFF;
    private static final byte SEGMENT_SOS = (byte) 0xDA;
    private static final byte MARKER_EOI = (byte) 0xD9;

    @SneakyThrows
    @NotNull
    public static int readSegments(@NotNull final SequentialReader reader) {
        int i = 0;
        // Must be big-endian
        assert (reader.isMotorolaByteOrder());

        // first two bytes should be JPEG magic number
        final int magicNumber = reader.getUInt16();
        i += 2;

        if (magicNumber != 0xFFD8) {
            throw new JpegProcessingException("JPEG data is expected to begin with 0xFFD8 (ÿØ) not 0x" + Integer.toHexString(magicNumber));
        }

        do {
            // Find the segment marker. Markers are zero or more 0xFF bytes, followed
            // by a 0xFF and then a byte not equal to 0x00 or 0xFF.

            byte segmentIdentifier = reader.getInt8();
            i++;
            byte segmentType = reader.getInt8();
            i++;

            // Read until we have a 0xFF byte followed by a byte that is not 0xFF or 0x00
            while (segmentIdentifier != SEGMENT_IDENTIFIER || segmentType == SEGMENT_IDENTIFIER || segmentType == 0) {
                segmentIdentifier = segmentType;
                segmentType = reader.getInt8();
                i++;
            }

            if (segmentType == SEGMENT_SOS) {
                // The 'Start-Of-Scan' segment's length doesn't include the image data, instead would
                // have to search for the two bytes: 0xFF 0xD9 (EOI).
                // It comes last so simply return at this point
                return i;
            }

            if (segmentType == MARKER_EOI) {
                // the 'End-Of-Image' segment -- this should never be found in this fashion
                return i;
            }

            // next 2-bytes are <segment-size>: [high-byte] [low-byte]
            int segmentLength = reader.getUInt16();
            i += 2;

            // segment length includes size bytes, so subtract two
            segmentLength -= 2;


            if (segmentLength < 0)
                throw new JpegProcessingException("JPEG segment size would be less than zero");


            // Skip this segment
            if (!reader.trySkip(segmentLength)) {
                // If skipping failed, just return the segments we found so far
                i += segmentLength;
                return i;
            }

        } while (true);
    }


    /**
     *
     */
    @SneakyThrows
    @Test
    public void unRarFromJpg() {
        InputStream inputStream = JpgFileExtractTest.class.getResourceAsStream("a.jpg");

        File dir = new File("/tmp/JpgFileExtractTest/unRarFromJpg");
        if (dir.exists()) {
            Assertions.assertTrue(dir.isDirectory());
            FileUtils.forceDelete(dir);
        }
        dir.mkdirs();

        List<File> files = Junrar.extract(inputStream, dir);
        files.stream().forEach(System.out::println);
    }

    /**
     * 虽然不保存，但也无结果
     */
    @SneakyThrows
    @Test
    public void unzip01() {

        File dir = new File("/tmp/JpgFileExtractTest/unzip01");
        if (dir.exists()) {
            Assertions.assertTrue(dir.isDirectory());
            FileUtils.forceDelete(dir);
        }
        dir.mkdirs();

        try (java.util.zip.ZipFile zipFile = new java.util.zip.ZipFile(new File("/tmp/a.jpg"))) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                File entryDestination = new File(dir, entry.getName());
                if (entry.isDirectory()) {
                    entryDestination.mkdirs();
                } else {
                    entryDestination.getParentFile().mkdirs();
                    try (InputStream in = zipFile.getInputStream(entry);
                         OutputStream out = new FileOutputStream(entryDestination)) {
                        IOUtils.copy(in, out);
                    }
                }
            }
            System.out.println("done.");
        }
    }

    /**
     * 异常，不行
     *
     * @throws IOException
     */
    @Test
    public void testZip4J() throws IOException {
        String dirPath = "/tmp/JpgFileExtractTest/testZip4J";
        File dir = new File(dirPath);
        if (dir.exists()) {
            Assertions.assertTrue(dir.isDirectory());
            FileUtils.forceDelete(dir);
        }
        dir.mkdirs();

        ZipFile zipFile = new ZipFile("/tmp/a.jpg");
        zipFile.extractAll(dirPath);
        System.out.println("testZip4J.done.");
    }
}

