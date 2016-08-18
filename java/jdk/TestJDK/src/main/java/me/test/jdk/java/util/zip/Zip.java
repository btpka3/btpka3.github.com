package me.test.jdk.java.util.zip;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.Pipe;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 创建zip文件。
 *
 *   1G = zipped 1020K
 *   3M = zipped    4K
 * 300M = zipped  300K
 */
public class Zip {

    public static void main(String[] args) throws IOException {

        String zipFile = "/tmp/big.zip";
        new File(zipFile).delete();

        FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
        CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream, new CRC32());
        ZipOutputStream zos = new ZipOutputStream(cos);

        ZipEntry entry = new ZipEntry("test.txt");
        zos.putNextEntry(entry);

        WritableByteChannel channel = Channels.newChannel(zos);

        ByteBuffer buf = ByteBuffer.allocate(1024 * 1024);

        int COUNT = 12;
        // COUNT =  300* 1024 * 1024;

        int c = 0;
        while (c < COUNT) {
            int fillCount = COUNT - c >= buf.capacity() ? buf.capacity() : COUNT - c;

            fillBuf(buf, fillCount);

            buf.flip();
            channel.write(buf);
            buf.compact();
            c += fillCount;
        }

        channel.close();
        System.out.printf("Done. see : " + zipFile);

    }

    static void fillBuf(ByteBuffer buf, int count) {

        assert count <= buf.remaining() : "count must less than remaining: " + count + ", " + buf.remaining();
        for (int i = 0; i < count; i++) {
            buf.put((byte) 'A');
        }

    }
}
