package io.github.btpka3.nexus.clean.snapshot.service.fs;

import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.nio.file.*;
import java.nio.file.FileSystem;
import java.nio.file.attribute.*;
import java.nio.file.spi.*;
import java.util.*;

/**
 * 尚未实现
 */
public class Nexus2FsProvider extends FileSystemProvider {

    @Override
    public String getScheme() {
        return "nexus2";
    }

    @Override
    public FileSystem newFileSystem(URI uri, Map<String, ?> env) throws IOException {
        throw new FileSystemException("Not Supported");
    }

    @Override
    public FileSystem getFileSystem(URI uri) {
        Nexus2Fs fs = new Nexus2Fs(this, uri);
        return fs;
    }

    @Override
    public Path getPath(URI uri) {
        return null;
    }

    @Override
    public SeekableByteChannel newByteChannel(
            Path path,
            Set<? extends OpenOption> options, FileAttribute<?>... attrs
    ) throws IOException {
        throw new FileSystemException("Not Supported");
    }

    @Override
    public DirectoryStream<Path> newDirectoryStream(
            Path dir,
            DirectoryStream.Filter<? super Path> filter
    ) throws IOException {
        throw new FileSystemException("Not Supported");
    }

    @Override
    public void createDirectory(
            Path dir,
            FileAttribute<?>... attrs
    ) throws IOException {
        throw new FileSystemException("Not Supported");
    }

    @Override
    public void delete(Path path) throws IOException {

    }

    @Override
    public void copy(Path source, Path target, CopyOption... options) throws IOException {
        throw new FileSystemException("Not Supported");
    }

    @Override
    public void move(Path source, Path target, CopyOption... options) throws IOException {
        throw new FileSystemException("Not Supported");
    }

    @Override
    public boolean isSameFile(Path path, Path path2) throws IOException {
        return false;
    }

    @Override
    public boolean isHidden(Path path) throws IOException {
        return false;
    }

    @Override
    public FileStore getFileStore(Path path) throws IOException {
        return null;
    }

    @Override
    public void checkAccess(Path path, AccessMode... modes) throws IOException {

    }

    @Override
    public <V extends FileAttributeView> V getFileAttributeView(Path path, Class<V> type, LinkOption... options) {
        return null;
    }

    @Override
    public <A extends BasicFileAttributes> A readAttributes(Path path, Class<A> type, LinkOption... options) throws IOException {
        return null;
    }

    @Override
    public Map<String, Object> readAttributes(Path path, String attributes, LinkOption... options) throws IOException {
        return null;
    }

    @Override
    public void setAttribute(Path path, String attribute, Object value, LinkOption... options) throws IOException {

    }
}
