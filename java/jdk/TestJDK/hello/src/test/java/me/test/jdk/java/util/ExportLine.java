package me.test.jdk.java.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.NoSuchElementException;
import java.util.Scanner;

/*
 导出指定的一行。
 */
public class ExportLine {

    final static String prefix = "INSERT INTO `trade` VALUES ";
    final static int bufSize = 1024 * 4;

    private static InputStream fromLargeFile() throws FileNotFoundException {
        final String dumpFile = "/home/zll/tmp/mysql_dump_140928.sql";
        return new BufferedInputStream(new FileInputStream(dumpFile));
    }

    public static void main(String[] args) throws IOException {

        Writer out = new FileWriter("/tmp/insert1.sql");
        Scanner sc = new Scanner(fromLargeFile());

        try {
            String str = null;
            while ((str = sc.findWithinHorizon(prefix, bufSize)) == null) {
                try {
                    sc.skip("(?s).{1," + (bufSize - prefix.length()) + "}");
                } catch (NoSuchElementException e) {
                    break;
                }
            }
            if (str != null) {
                out.append(str);

                String lineEnd = ".*\\);";
                while ((str = sc.findWithinHorizon(lineEnd, bufSize)) == null) {
                    out.append(sc.findWithinHorizon("(?s).{1," + (bufSize - 2) + "}", bufSize));
                }
                out.append(str);

            } else {
                System.out.println("Not found.");
            }
        } finally {
            out.flush();
            out.close();
            sc.close();
            System.out.println("Done.");
        }
    }
}
