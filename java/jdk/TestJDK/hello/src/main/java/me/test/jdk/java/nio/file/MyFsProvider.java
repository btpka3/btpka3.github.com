package me.test.jdk.java.nio.file;

import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.nio.file.*;
import java.nio.file.FileSystem;
import java.nio.file.attribute.*;
import java.nio.file.spi.*;
import java.util.*;

public class MyFsProvider extends FileSystemProvider {


    public static void main(String[] args) throws IOException {

        List<String> entries = Arrays.asList(
                "/a/",
                "/a/a1",
                "/a/a2",
                "/b/b1",
                "/b/b2/",
                "/b/b2/b3"
        );


        Files.walk(Paths.get("my://zll/"))
                //.peek(System.out::println)
                .forEach(System.out::println);

    }

    public static final String SCHEMA = "my";

    private Map<String, MyFs> registry = new LinkedHashMap<>();

    @Override
    public String getScheme() {
        return SCHEMA;
    }

    private void checkUri(URI uri) {
        if (uri.getScheme().equals(SCHEMA)) {
            throw new IllegalArgumentException("只支持 scheme 为 '" + SCHEMA + "'");
        }
    }

    @Override
    public FileSystem newFileSystem(URI uri, Map<String, ?> env) throws IOException {

        checkUri(uri);


        if (registry.containsKey(uri.getHost())) {
            throw new IllegalArgumentException("名称 : '" + uri.getHost() + "' 已经注册了");
        }
        String httpBaseUrl = (String) env.get("httpBaseUrl");
        String username = (String) env.get("username");
        String password = (String) env.get("password");
        MyFs fs = new MyFs(this, httpBaseUrl, username, password);
        registry.put(username, fs);
        return fs;
    }

    @Override
    public FileSystem getFileSystem(URI uri) {
        checkUri(uri);

        return registry.get(uri.getHost());
    }

    @Override
    public Path getPath(URI uri) {
        return this.getFileSystem(uri).getPath(uri.getPath());
    }

    @Override
    public SeekableByteChannel newByteChannel(Path path, Set<? extends OpenOption> options, FileAttribute<?>... attrs) throws IOException {
        throw new IllegalArgumentException("Not Supported");
    }

    @Override
    public DirectoryStream<Path> newDirectoryStream(Path dir, DirectoryStream.Filter<? super Path> filter) throws IOException {
        throw new IllegalArgumentException("Not Supported");
    }

    @Override
    public void createDirectory(Path dir, FileAttribute<?>... attrs) throws IOException {
        throw new IllegalArgumentException("Not Supported");
    }

    @Override
    public void delete(Path path) throws IOException {

    }

    @Override
    public void copy(Path source, Path target, CopyOption... options) throws IOException {
        throw new IllegalArgumentException("Not Supported");
    }

    @Override
    public void move(Path source, Path target, CopyOption... options) throws IOException {
        throw new IllegalArgumentException("Not Supported");
    }

    @Override
    public boolean isSameFile(Path path, Path path2) throws IOException {
        return path.toAbsolutePath().equals(path2.toAbsolutePath());
    }

    @Override
    public boolean isHidden(Path path) throws IOException {
        return false;
    }

    @Override
    public FileStore getFileStore(Path path) throws IOException {
        //return path.getFileStore(); // TODO
        return null;
    }

    @Override
    public void checkAccess(Path path, AccessMode... modes) throws IOException {
        // TODO
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
