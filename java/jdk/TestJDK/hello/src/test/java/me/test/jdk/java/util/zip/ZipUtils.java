package me.test.jdk.java.util.zip;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.IOUtils;

/**
 *
 */
public class ZipUtils {

    public static void createZipFile(Path src, Path zipFile) throws IOException {

        Path zipFileDir = zipFile.getParent();
        if (!Files.exists(zipFileDir)) {
            Files.createDirectories(zipFileDir);
        }

        try (OutputStream fos = Files.newOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos)
        ) {
            Files.walkFileTree(
                    src,
                    Collections.emptySet(),
                    //EnumSet.of(FileVisitOption.FOLLOW_LINKS),
                    Integer.MAX_VALUE,
                    new PathSimpleFileVisitor(src, zos)
            );
        }
    }

    protected static class PathSimpleFileVisitor extends SimpleFileVisitor<Path> {

        private final Path src;
        private final ZipOutputStream zos;

        public PathSimpleFileVisitor(Path src, ZipOutputStream zos) {
            this.src = src;
            this.zos = zos;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

            String relative = src.relativize(dir).toString();
            if (relative.isEmpty()) {
                return FileVisitResult.CONTINUE;
            }
            String entryName = relative + "/";
            ZipEntry zipEntry = toZipEntry(entryName, attrs);
            zos.putNextEntry(zipEntry);
            zos.closeEntry();
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                throws IOException {
            String relative = src.relativize(file).toString();

            String entryName = relative.isEmpty()
                    ? file.getFileName().toString()
                    : relative;
            ZipEntry zipEntry = toZipEntry(entryName, attrs);
            zos.putNextEntry(zipEntry);
            try (InputStream in = Files.newInputStream(file)) {
                IOUtils.copy(in, zos);
            }
            zos.closeEntry();
            return FileVisitResult.CONTINUE;
        }

        protected ZipEntry toZipEntry(String entryName, BasicFileAttributes attrs) {
            ZipEntry entry = new ZipEntry(entryName);
            entry.setLastModifiedTime(attrs.lastModifiedTime());
            entry.setCreationTime(attrs.creationTime());
            entry.setLastAccessTime(attrs.lastAccessTime());
            return entry;
        }
    }
}
