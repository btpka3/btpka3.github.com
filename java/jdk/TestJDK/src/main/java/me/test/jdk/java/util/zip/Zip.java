package me.test.jdk.java.util.zip;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.Pipe;
import java.nio.channels.ReadableByteChannel;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 创建zip文件
 */
public class Zip {

    public static void main(String[] args) throws IOException {

        String zipFile = "/tmp/big.zip";
        FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
        CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream, new CRC32());
        ZipOutputStream zos = new ZipOutputStream(cos);

        ZipEntry entry = new ZipEntry("zip.txt");
        zos.putNextEntry(entry);

//        zos.write(data, 0, count);
//        compress(file, out, basedir);
//        out.close();

//        ReadableByteChannel rbc = Channels.newChannel();
//
//        Pipe p = Pipe.open();
//        p.sink().write()
    }

    static class R implements Runnable{

        @Override
        public void run() {

        }
    }
}
