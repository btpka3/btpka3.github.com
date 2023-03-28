package me.test.jdk.java.nio.file;

import java.io.*;
import java.nio.file.*;
import java.nio.file.FileSystem;
import java.nio.file.attribute.*;
import java.nio.file.spi.*;
import java.util.*;

public class MyFs extends FileSystem {

    private MyFsProvider provider;
    private String httpBaseUrl;
    private String username;
    private String password;

    private Queue lruCache; // FIXME:

    public static <K,V> Map<K,V> lruCache(final int maxSize) {
        return new LinkedHashMap<K, V>(maxSize*4/3, 0.75f, true) {

            private static final long serialVersionUID = -3588047435434569014L;

            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > maxSize;
            }
        };
    }

    public MyFs(
            MyFsProvider provider,
            String httpBaseUrl,
            String username,
            String password
    ) {
        this.provider = provider;
        this.httpBaseUrl = httpBaseUrl;
        this.username = username;
        this.password = password;
    }

    @Override
    public FileSystemProvider provider() {
        return this.provider;
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public String getSeparator() {
        return null;
    }

    @Override
    public Iterable<Path> getRootDirectories() {
        return null;
    }

    @Override
    public Iterable<FileStore> getFileStores() {
        return null;
    }

    @Override
    public Set<String> supportedFileAttributeViews() {
        return null;
    }

    @Override
    public Path getPath(String first, String... more) {
        return null;
    }

    @Override
    public PathMatcher getPathMatcher(String syntaxAndPattern) {
        return null;
    }

    @Override
    public UserPrincipalLookupService getUserPrincipalLookupService() {
        throw new IllegalArgumentException("Not Supported");
    }

    @Override
    public WatchService newWatchService() throws IOException {
        throw new IllegalArgumentException("Not Supported");
    }
}
